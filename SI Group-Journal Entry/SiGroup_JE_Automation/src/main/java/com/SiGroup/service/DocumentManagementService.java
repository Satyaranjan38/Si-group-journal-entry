package com.SiGroup.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.SiGroup.dms.util.ApplicationConstants;
import com.SiGroup.dto.DmsGetResponseDto;
import com.SiGroup.dto.ResponseDto;

import org.apache.chemistry.opencmis.commons.SessionParameter;

@Service
public class DocumentManagementService {

	public static final String OAUTH_ACCESS_TOKEN = "org.apache.chemistry.opencmis.oauth.accessToken";

	private Map<String, String> getRepositorySessionConnection() {

		Map<String, String> parameter = new HashMap<String, String>();

		// connection settings
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
		parameter.put(SessionParameter.BROWSER_URL, "https://api-sdm-di.cfapps.eu10.hana.ondemand.com/browser");
		parameter.put(SessionParameter.OAUTH_ACCESS_TOKEN, getAccessToken());
		parameter.put(SessionParameter.AUTH_HTTP_BASIC, "false");
		parameter.put(SessionParameter.AUTH_SOAP_USERNAMETOKEN, "false");
		parameter.put(SessionParameter.AUTH_OAUTH_BEARER, "true");
		parameter.put(SessionParameter.USER_AGENT,
				"OpenCMIS-Workbench/1.1.0 Apache-Chemistry-OpenCMIS/1.1.0 (Java 1.8.0_271; Windows 10 10.0)");

		return parameter;

	}

	private static String getFileExtension(String fullName) {
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);

	}

	private String getAccessToken() {
		/* HTTPCLIENT AND HTTPPOST OOBJECT */
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(ApplicationConstants.DMS_TOKEN_ENDPOINT);

		/* AUTHENTICATION CREDENTIALS ENCODING */
		String base64Credentials = Base64.getEncoder().encodeToString(
				(ApplicationConstants.DMS_CLIENT_ID + ":" + ApplicationConstants.DMS_CLIENT_SECRET).getBytes());

		/* HEADER INFO */
		httpPost.addHeader("Authorization", "Basic " + base64Credentials);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

		/* OAUTH PARAMETERS ADDED TO BODY */
		StringEntity input = null;
		try {
			input = new StringEntity("grant_type=" + ApplicationConstants.DMS_GRANT_TYPE);
			httpPost.setEntity(input);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/* SEND AND RETRIEVE RESPONSE */
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* RESPONSE AS STRING */
		String result = null;
		try {
			result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject o = new JSONObject(result);
		System.out.println(o.getString("access_token").toString());
		System.out.println(o.getString("access_token").toString());
		return o.getString("access_token").toString();

	}

	private String getMIMEtype(String extension) {
		Map map = new HashMap();
		map.put("3gp", "video/3gpp");
		map.put("3g2", "video/3gpp2");
//		map.put("7z", "application/x-7z-compressed");
//		map.put("aac", "audio/aac");
//		map.put("abw", "application/x-abiword");
//		map.put("arc", "application/x-freearc");
		map.put("avi", "video/x-msvideo");
//		map.put("azw", "application/vnd.amazon.ebook");
//		map.put("bin", "application/octet-stream");
		map.put("bmp", "image/bmp");
//		map.put("bz", "application/x-bzip");
//		map.put("bz2", "application/x-bzip2");
//		map.put("csh", "application/x-csh");
//		map.put("css ", "text/css");
//		map.put("csv", "text/csv");
		map.put("doc", "application/msword");
		map.put("docx", "application/vnd.openxmlformats officedocument.wordprocessingml.document");
//		map.put("eot", "application/vnd.ms-fontobject");
//		map.put("epub", "application/epub+zip");
		map.put("gif", "image/gif");
//		map.put("htm/.html", "text/html");
//		map.put("ico", "image/vnd.microsoft.icon");
//		map.put("ics", "text/calendar");
//		map.put("jar", "application/java-archive");
		map.put("jpg", "image/jpg");
		map.put("jpeg", "image/jpeg");
//		map.put("js", "text/javascript");
//		map.put("json", "application/json");
//		map.put("mid/.midi", "audio/midi, audio/x-midi");
//		map.put("mjs", "text/javascript");
//		map.put("mp3", "audio/mpeg");
		map.put("mpeg", "video/mpeg");
//		map.put("mpkg", "application/vnd.apple.installer+xml");
//		map.put("odp", "application/vnd.oasis.opendocument.presentation");
//		map.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
//		map.put("odt", "application/vnd.oasis.opendocument.text");
//		map.put("oga", "audio/ogg");
		map.put("ogv", "video/ogg");
//		map.put("ogx", "application/ogg");
//		map.put("otf", "font/otf");
		map.put("png", "image/png");
		map.put("pdf", "application/pdf");
		map.put("ppt", "application/vnd.ms-powerpoint");
		map.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
//		map.put("rar", "application/x-rar-compressed");
//		map.put("rtf", "application/rtf");
//		map.put("sh", "application/x-sh");
//		map.put("svg", "image/svg+xml");
//		map.put("swf", "application/x-shockwave-flash");
//		map.put("tar", "application/x-tar");
		map.put("tif", "image/tiff");
//		map.put("ttf", "font/ttf");
		map.put("txt", "text/plain");
//		map.put("vsd", "application/vnd.visio");
//		map.put("wav", "audio/wav");
//		map.put("weba", "audio/webm");
		map.put("webm", "video/webm");
		map.put("webp", "image/webm");
//		map.put("woff", "font/woff");
//		map.put("woff2", "font/woff2");
//		map.put("xhtml", "application/xhtml+xml");
		map.put("xls", "application/vnd.ms-excel");
		map.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//		map.put("xml", "application/xml");
//		map.put("xul", "application/vnd.mozilla.xul+xml");
//		map.put("zip", "application/zip");
		return map.get(extension).toString();
	}

	public ResponseDto uploadDocument(File file, String permitNumber, String company) {

		// Getting the current date
		Date date = new Date();
		// This method returns the time in millis
		long timeMilli = date.getTime();

		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		fileName = fileName.substring(0, index) + timeMilli;
		double fileSizeKb = (double) file.length() / 1024;
		double fileSizeMb = (double) file.length() / (1024 * 1024);
		String fileSize = "";
		if (fileSizeMb >= 1.0)
			fileSize = fileSizeMb + "MB";
		else
			fileSize = fileSizeKb + "KB";
		System.out.println("File Size in MB/KB==" + fileSize);
		System.out.println("File Name::::" + fileName);
		System.out.println("[Foulath PTW][DocumentManagement][ServiceImpl][File] = " + file.getName());
		ResponseDto response = new ResponseDto();
		int count = 0;
		try {
			String folderName = permitNumber;
			// default factory implementation
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = getRepositorySessionConnection();
			Session session = factory.getRepositories(parameter).get(0).createSession();

			Folder root = session.getRootFolder();
			Folder parent = null;
			// Folder child=null;
			String DMS_FOLDER;
			if (company.trim().equalsIgnoreCase("BS") == true) {
				DMS_FOLDER = ApplicationConstants.DMS_BS_ROOT_FOLDER;
				System.out.println(" CHILD FOLDER===" + DMS_FOLDER);
				parent = (Folder) session.getObject(DMS_FOLDER);
			} else {
				DMS_FOLDER = ApplicationConstants.DMS_SULB_ROOT_FOLDER;
				System.out.println("CHILD FOLDER===" + DMS_FOLDER);
				parent = (Folder) session.getObject(DMS_FOLDER);
			}
			Folder child = null;
			String folderId = null;
			ItemIterable<CmisObject> childs = parent.getChildren();
			for (CmisObject O : childs) {
				System.out.println(O.getName());
				if (O.getName().equalsIgnoreCase(folderName)) {
					folderId = O.getId();
					child = (Folder) O;
					System.out.println(folderId);
				}
			}
			String extension = getFileExtension(file.getName());
			Map<String, Object> properties2 = new HashMap<String, Object>();
			properties2.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties2.put(PropertyIds.NAME, file.getName() + "(" + timeMilli + ")." + extension);

			// if folder is not there

			if (child == null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
				properties.put(PropertyIds.NAME, folderName); // folder
																// name

				child = parent.createFolder(properties);
			} else {

				count = 0;
				CmisObject o = session.getObject(folderId);
				child = (Folder) o;
				ItemIterable<CmisObject> documents = child.getChildren();
				System.out.println(documents);
				for (CmisObject O : documents) {
					System.out.println(O.getId());
					CmisObject document = session.getObject(O.getId());
					System.out.println(document);
					Document doc = (Document) session.getObject(O.getId());
					ContentStream contentStream = doc.getContentStream(); // returns
																			// null
																			// if
																			// the
																			// document
																			// has
																			// no
																			// content
//							if (doc.getName().equals(file.getName())) {
//								System.out.println("Deleting document " + doc.getName());
//								doc.delete(true);
//							}
					//

				}
			}

			System.out.println(file.getName());
			String extention = getFileExtension(file.getName());

			String mimeType = getMIMEtype(extention.toLowerCase());

			InputStream targetStream = new FileInputStream(file);
			ContentStream contentStream = new ContentStreamImpl(file.getName() + "(" + timeMilli + ")." + extension,
					BigInteger.valueOf(file.length()), mimeType, targetStream);
			// create a major version
			Document newDoc = child.createDocument(properties2, contentStream, VersioningState.MAJOR);

			System.out.println(newDoc.getId());
			response.setCode(ApplicationConstants.CODE_SUCCESS);
			response.setStatus(ApplicationConstants.SUCCESS);
			response.setMessage("Document Updated Succesfully");
			response.setDocumentId(newDoc.getId());
			response.setFileName(fileName);
			response.setFileSize(fileSize);
			count = count + 1;

		} catch (Exception e) {
			response.setCode(ApplicationConstants.CODE_FAILURE);
			response.setStatus(ApplicationConstants.FAILURE);
			response.setMessage("Failed due to " + e.getMessage());
		}

		return response;
	}

	public DmsGetResponseDto getDocument(String documentId) {
		// TODO Auto-generated method stub
		DmsGetResponseDto response = new DmsGetResponseDto();

		try {

			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = getRepositorySessionConnection();
			Session session = factory.getRepositories(parameter).get(0).createSession();

			Folder root = session.getRootFolder();
			ItemIterable<CmisObject> insideRootFolder = root.getChildren();
			Document doc = (Document) session.getObject(documentId);
			System.out.println();
			ContentStream contentStream = doc.getContentStream(); // returns

			if (contentStream != null) {

				InputStream is = contentStream.getStream();
				byte[] bytes = IOUtils.toByteArray(is);
				String encoded = Base64.getEncoder().encodeToString(bytes);
				response.setBase64(encoded);
				response.setMimeType(contentStream.getMimeType());
				response.setDocumentName(doc.getName());
				response.setFileAvailability(true);
				System.out.println(encoded);
			} else {
				response.setBase64(null);
				response.setMimeType(null);
				response.setFileAvailability(false);
			}

		} catch (Exception e) {
			response.setBase64(null);
			response.setMimeType(null);
			response.setFileAvailability(false);

		}
		System.out.println("DMS Get Response::::::" + response);
		return response;

	}
	


}
