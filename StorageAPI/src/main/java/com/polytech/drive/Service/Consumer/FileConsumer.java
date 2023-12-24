package com.polytech.drive.Service.Consumer;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.polytech.drive.DTO.FileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(FileConsumer.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @KafkaListener(id = "files-consumers-group", topics = "files-save-topic", concurrency = "10")
    public void save(FileDTO fileDTO) {
        try {
            File file = fileDTO.getFile();
            LOG.info("Uploading file with name {}", fileDTO.getEmail() + file.getName());
            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileDTO.getEmail() + "/" + file.getName(), file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath());
        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException ex) {
            LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
        }
    }
}
