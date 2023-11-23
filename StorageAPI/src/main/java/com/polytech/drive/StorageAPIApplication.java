package com.polytech.drive;

import com.polytech.drive.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StorageAPIApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(StorageAPIApplication.class, args);
	}

	@Autowired
	private FileService fileService;

	@Value("${s3.bucket.name}")
	private String s3BucketName;
	@Override
	public void run(String... args) throws Exception {
		fileService.createBucket(s3BucketName);
	}
}