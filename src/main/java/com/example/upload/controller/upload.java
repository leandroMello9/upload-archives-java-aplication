package com.example.upload.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class upload {

    private final Integer permissionSize = 1000000000;
    
    @PostMapping("/upload")
    public ResponseEntity<?> doUpaload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {



            File newFile = new File("/home/leandro/java-projects/upload/tmp/" + Math.random() + fileName);

            System.out.println(Files.probeContentType(newFile.toPath()));

            if(Files.probeContentType(newFile.toPath()) == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File isn't valid");
            }
            if(file.getSize() >= permissionSize) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            file.transferTo(newFile);
            return ResponseEntity.ok("FIle uploaed successfully");
            
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);

        }
        
    }
}
