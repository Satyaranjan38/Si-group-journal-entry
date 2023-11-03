package com.SiGroup.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SiGroup.dms.util.ByteArrayToPDF;
import com.SiGroup.service.StorageService;
import com.itextpdf.text.Document;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping()
public class ImageController {

	public final String outputFilePath = "D:\\file";

	@Autowired
	private StorageService service;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

		ResponseEntity<?> uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@GetMapping("/download/{fileType}/{fileName}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName) {
		byte[] fileData = service.downloadImage(fileName);

		HttpHeaders headers = new HttpHeaders();
		InputStream is;
		String contentType;

		try {

			is = new ByteArrayInputStream(fileData);
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int bytesRead;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			byte[] outputBytes = outputStream.toByteArray();
			headers.setContentType(MediaType.parseMediaType(contentType));
			headers.setContentDispositionFormData("attachment", fileName);

			return new ResponseEntity<>(outputBytes, headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/delete/{name}")
	public String deleteDate(@PathVariable String name) {
		return service.deleteByName(name);
	}

}
