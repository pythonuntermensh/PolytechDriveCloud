package com.polytech.drive.Controller;

import com.polytech.drive.DTO.FileDTO;
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

    @GetMapping()
    public String getAllFiles(Model model) {
        return "";
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile multipartFile) {
        File file = fileProducer.convertMultiPartFileToFile(multipartFile);
        fileProducer.sendFile(new FileDTO(file, LocalDateTime.now()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
