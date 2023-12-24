package com.polytech.drive.Service;

import com.polytech.drive.DTO.FileDTO;
import com.polytech.drive.Entity.FileEntity;
import com.polytech.drive.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public void save(FileDTO file) {
        FileEntity fileEntity = new FileEntity(file.getFile().getName(), file.getTimestamp().getLocalDateTime());
        fileRepository.save(fileEntity);
    }
}
