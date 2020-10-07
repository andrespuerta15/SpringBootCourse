package com.bolsadeideas.springboot.jpa.app.models.services;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	Resource loadFile(String pFileName) throws MalformedURLException;

	String copyFile(MultipartFile pFile) throws IOException;

	boolean deleteFile(String pFileName);
	
	void deleteAll();
	
	void init() throws IOException;

}
