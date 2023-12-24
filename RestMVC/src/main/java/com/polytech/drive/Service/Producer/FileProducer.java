package com.polytech.drive.Service.Producer;

import com.polytech.drive.DTO.FileDTO;
import com.polytech.drive.DTO.TimestampDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileProducer {

    private static final Logger LOG = LoggerFactory.getLogger(FileProducer.class);
    private final KafkaTemplate<String, Object> template;

    public FileProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendFile(FileDTO file) {
        template.send("files-save-topic", file);
    }

    public File convertMultiPartFileToFile(final MultipartFile multipartFile, TimestampDTO timestamp) {
        final File file = new File(timestamp.getLocalDateTimeInCorrectFormat() + "_" + multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file);
             final InputStream inputStream = multipartFile.getInputStream()) {
            byte[] buffer = new byte[4194304];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
           LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }
}
