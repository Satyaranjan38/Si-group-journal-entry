package com.SiGroup.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SiGroup.dao.JdbcRepo;
import com.SiGroup.dao.StorageRepository;
import com.SiGroup.dms.util.ImageUtils;
import com.SiGroup.entites.ImageData;

@Service
public class StorageService {

	
	@Autowired
    private StorageRepository repository;
	
	@Autowired
	private JdbcRepo jdbcRepo ; 

    public ResponseEntity<?> uploadImage(MultipartFile file) throws IOException {
    	
    	System.out.println(file.getContentType());
    	
    	java.util.Optional<ImageData> existingPerson = repository.findByName(file.getName());

    	if(file.getContentType()==null) {
    		 return new ResponseEntity<>("Must contains a file" , HttpStatus.BAD_REQUEST);
    	}
    	
    	
		/*
		 * if(jdbcRepo.isRecordExists(file.getName())) { ImageData updatedimageDate =
		 * new ImageData();
		 * 
		 * String uniqueName = System.currentTimeMillis()+"_"+file.getName();
		 * updatedimageDate.setName(uniqueName);
		 * 
		 * 
		 * }
		 */
    	String uniqueName = null ;
    	
    	if (file.getOriginalFilename().contains("pdf")) {
    		 uniqueName = removeFileExtension(file.getOriginalFilename())+System.currentTimeMillis()+".pdf";
    	}
    	if(file.getOriginalFilename().contains("xlsx")) {
    		
    		
    		uniqueName = removeFileExtension(file.getOriginalFilename())+System.currentTimeMillis()+".xlsx";
    	}
    	
    	
        ImageData imageData = repository.save(ImageData.builder().name(uniqueName)
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return  new ResponseEntity<>("file uploaded successfully : " + uniqueName,HttpStatus.OK);
        }
        
        return new ResponseEntity<>("Must contains a file" , HttpStatus.BAD_REQUEST);
        
    }
    
    public byte[] downloadImage(String fileName){
        java.util.Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Transactional
	public String  deleteByName(String name) {
		// TODO Auto-generated method stub
		repository.deleteByName(name);
		return name+" Deleted sucessfully";
	}
    
    
    public String removeFileExtension(String fileName) {
        return fileName.replaceAll("\\.\\w+$", "");
    }

	
	
}
