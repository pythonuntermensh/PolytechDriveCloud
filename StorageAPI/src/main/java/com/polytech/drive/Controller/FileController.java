package com.polytech.drive.Controller;

import com.polytech.drive.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

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