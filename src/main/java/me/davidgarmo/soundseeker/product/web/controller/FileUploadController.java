package me.davidgarmo.soundseeker.product.web.controller;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {
    private final IFileUploadService fileUploadService;

    public FileUploadController(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file) {
        Map<String, String> fileInfo = fileUploadService.uploadFile(file);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("filePath", fileInfo.get("filePath"));
        response.put("fileName", fileInfo.get("fileName"));
        response.put("originalFileName", fileInfo.get("originalFileName"));

        return ResponseEntity.status(201).body(response);
    }
}
