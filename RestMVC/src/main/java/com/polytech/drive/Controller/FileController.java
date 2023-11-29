package com.polytech.drive.Controller;

import com.polytech.drive.DTO.FileSendDTO;
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
        File file = convertMultiPartFileToFile(multipartFile);
        fileProducer.sendFile(new FileSendDTO(file, LocalDateTime.now()).toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            //LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }
}
