package com.polytech.drive.Controller;

import com.amazonaws.services.s3.model.ObjectListing;
import com.polytech.drive.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private static final String MESSAGE_1 = "Uploaded the file successfully";

    @Autowired
    FileService fileService;

    @GetMapping
    public ResponseEntity<Object> findByName(@RequestParam("fileName") String fileName) {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(fileService.findByName(fileName)));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile) {
        fileService.save(multipartFile, LocalDateTime.now());
        return new ResponseEntity<>(MESSAGE_1, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("fileName") String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        fileService.deleteAll();
        return ResponseEntity.ok().build();
    }
}