package com.polytech.drive.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    public void createBucket(String s3BucketName) {
        if (amazonS3.doesBucketExistV2(s3BucketName)) {
            LOG.warn("Bucket with this name already exists. Try another one");
            return;
        }
        if (amazonS3.doesBucketExist(s3BucketName)) {
            LOG.warn("Bucket with this name already exists. Try another one");
            return;
        }
        amazonS3.createBucket(s3BucketName);
    }

    public List<String> findAll() {
        ObjectListing objectListing = amazonS3.listObjects(s3BucketName);
        List<S3ObjectSummary> s3ObjectSummaryList = objectListing.getObjectSummaries();
        return s3ObjectSummaryList.stream().map(S3ObjectSummary::getKey).toList();
    }

    public S3ObjectInputStream findByName(String fileName) {
        LOG.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
    }

    public List<String> findAllByPrefix(String prefix) {
        return amazonS3.listObjects(s3BucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .filter(key -> key.startsWith(prefix))
                .toList();
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