package com.SiGroup.dto;

public class DmsGetResponseDto {

	
	private String base64;
	private String mimeType;
	private String documentName;
	private Boolean fileAvailability;
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public Boolean getFileAvailability() {
		return fileAvailability;
	}
	public void setFileAvailability(Boolean fileAvailability) {
		this.fileAvailability = fileAvailability;
	}
	@Override
	public String toString() {
		return "DmsGetResponseDto [base64=" + base64 + ", mimeType=" + mimeType + ", documentName=" + documentName
				+ ", fileAvailability=" + fileAvailability + "]";
	}
	
	
}
