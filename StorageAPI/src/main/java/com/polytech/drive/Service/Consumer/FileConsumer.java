package com.polytech.drive.Service.Consumer;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Service
public class FileConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(FileConsumer.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @KafkaListener(id = "files-consumers-group", topics = "files-save-topic", concurrency = "3")
    public void save(String msg) {
        try {
            String[] parts = msg.split(":::");
            String timestamp = parts[0];
            String name = parts[1];
            List<Byte> bytesList = Arrays.stream(parts[2].substring(1, parts[2].length() - 1).split(", ")).map(Byte::parseByte).toList();
            byte[] bytes = new byte[bytesList.size()];
            for(int i = 0; i < bytesList.size(); i++) {
                bytes[i] = bytesList.get(i);
            }
            final File file = new File(name);
            Files.write(file.toPath(), bytes);
            final String fileName = timestamp + ":::" + file.getName();
            LOG.info("Uploading file with name {}", fileName);
            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileName, file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath());
        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException ex) {
            LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
        }
    }
}
