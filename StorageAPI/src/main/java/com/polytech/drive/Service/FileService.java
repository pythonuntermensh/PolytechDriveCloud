package com.polytech.drive.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    public void createBucket(String s3BucketName) {
        if (amazonS3.doesBucketExistV2(s3BucketName)) {
            LOG.warn("Bucket with this name already exists. Try another one");
            return;
        }
        amazonS3.createBucket(s3BucketName);
    }

    @Async
    public List<String> findAll() {
        ObjectListing objectListing = amazonS3.listObjects(s3BucketName);
        List<S3ObjectSummary> s3ObjectSummaryList = objectListing.getObjectSummaries();
        return s3ObjectSummaryList.stream().map(S3ObjectSummary::getKey).toList();
    }

    @Async
    public S3ObjectInputStream findByName(String fileName) {
        LOG.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
    }

    @Async
    public void save(final MultipartFile multipartFile, LocalDateTime timestamp) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            final String fileName = timestamp + "_" + file.getName();
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

    @Async
    public void deleteFile(String fileName) {
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Async
    public void deleteAll() {
        List<String> fileToDelete = findAll();
        fileToDelete.forEach(this::deleteFile);
    }
}