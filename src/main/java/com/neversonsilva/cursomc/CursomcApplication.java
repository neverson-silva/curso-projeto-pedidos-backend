package com.neversonsilva.cursomc;

import com.neversonsilva.cursomc.services.S3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{


	@Autowired
	private S3Service s3;
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	
	public void run(String... args) throws Exception {
		s3.uploadFile("C:\\tmp\\ricky.jpg");

	}

}
