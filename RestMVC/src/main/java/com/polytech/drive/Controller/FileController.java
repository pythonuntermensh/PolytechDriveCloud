package com.polytech.drive.Controller;

import com.polytech.drive.Client.StorageApiClient;
import com.polytech.drive.DTO.FileDTO;
import com.polytech.drive.DTO.TimestampDTO;
import com.polytech.drive.Response.ListFileResponse;
import com.polytech.drive.Service.FileService;
import com.polytech.drive.Service.Producer.FileProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
    private final FileProducer fileProducer;
    private final FileService fileService;

    private final StorageApiClient storageApiClient;

    @Autowired
    public FileController(FileProducer fileProducer, FileService fileService, StorageApiClient storageApiClient){
        this.fileProducer = fileProducer;
        this.fileService = fileService;
        this.storageApiClient = storageApiClient;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFileByName(@RequestParam("fileName") String fileName, Principal principal) throws IOException {
        var resource = storageApiClient.downloadFileByName(principal.getName() + "/" + fileName);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(resource.getBody().getInputStream()));
    }

    @GetMapping()
    public ResponseEntity<ListFileResponse> getAllFiles(Principal principal) {
        System.out.println(principal.getName());
        return storageApiClient.findAllByPrefix(principal.getName());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        TimestampDTO timestamp = new TimestampDTO(LocalDateTime.now());
        File file = fileProducer.convertMultiPartFileToFile(multipartFile, timestamp);

        FileDTO fileDTO = new FileDTO(file, timestamp, principal.getName());

        fileProducer.sendFile(fileDTO);
        fileService.save(fileDTO); // TODO: redo with callback

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
