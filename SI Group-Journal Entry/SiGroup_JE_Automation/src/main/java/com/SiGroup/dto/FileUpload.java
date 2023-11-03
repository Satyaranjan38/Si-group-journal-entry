package com.SiGroup.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUpload {

	
	public final String upload_dir = "D:\\file";
	
	public boolean uploadFile(MultipartFile multiPartFile) {
		
		
		boolean f = false ;
		
		try {
			Files.copy(multiPartFile.getInputStream(),Paths.get(upload_dir+File.separator+multiPartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			f= true ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return f ; 
	}
}
