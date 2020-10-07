package com.bolsadeideas.springboot.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.jpa.app.models.services.IUploadFileService;

@SpringBootApplication
public class SpringBootJpaApplication implements CommandLineRunner {

	@Autowired
	private IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}
