package com.SiGroup.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SiGroup.dms.util.ServiceUtil;
import com.SiGroup.dto.ResponseDto;
import com.SiGroup.service.DocumentManagementService;

@RestController
@RequestMapping("/api")
public class DocumentManagementController {

	private DocumentManagementService documentManagementService ; 
	
	/*
	 * @PostMapping("/upload1") ResponseDto uploadDocument(@RequestParam("file")
	 * MultipartFile multipartFile, @RequestParam("requestId") String
	 * requestId, @RequestParam("company") String company) throws IOException { File
	 * file = ServiceUtil.multipartToFile(multipartFile); return
	 * documentManagementService.uploadDocument(file,requestId,company);
	 * 
	 * }
	 */
}
