package com.polytech.drive.Controller;

import com.polytech.drive.DTO.FileDTO;
import com.polytech.drive.Service.FileService;
import com.polytech.drive.Service.Producer.FileProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileProducer fileProducer;

    @Autowired
    private FileService fileService;

    @GetMapping()
    public String getAllFiles(Model model) {
        return "";
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile multipartFile) {
        LocalDateTime timestamp = LocalDateTime.now();
        File file = fileProducer.convertMultiPartFileToFile(multipartFile, timestamp.toString());

        FileDTO fileDTO = new FileDTO(file, timestamp);

        fileProducer.sendFile(fileDTO);
        fileService.save(fileDTO); // TODO: redo with callback

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
