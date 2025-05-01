package me.davidgarmo.soundseeker.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IFileUploadService {
    void init();

    Map<String, String> uploadFile(MultipartFile file);

    byte[] serveFile(String fileName);

    String getFileContentType(String fileName);
}
