package com.polytech.drive.Service.Producer;

import com.polytech.drive.DTO.FileSendDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FileProducer {
    private final KafkaTemplate<String, String> template;

    public FileProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendFile(String file) {
        template.send("files-save-topic", file);
    }
}
