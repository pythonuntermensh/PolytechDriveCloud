package com.polytech.drive.Controller;

import com.polytech.drive.Response.ListFileResponse;
import com.polytech.drive.Service.FileService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Value("${server.port}")
    private String port;

    @GetMapping()
    public ResponseEntity<ListFileResponse> findAllByEmail(@RequestParam("prefix") String prefix) {
        System.out.println(instanceId);
        System.out.println(port);
        return ResponseEntity.ok(fileService.findAllByPrefix(prefix));
    }

    @GetMapping("/get")
    public ResponseEntity<Resource> findByName(@RequestParam("fileName") String fileName) {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(fileService.findByName(fileName)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> findAll() {
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