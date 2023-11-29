package com.polytech.drive.Service.Producer;

import com.polytech.drive.DTO.FileDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FileProducer {
    private final KafkaTemplate<String, Object> template;

    public FileProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendFile(FileDTO file) {
        template.send("files-save-topic", file);
    }
}
