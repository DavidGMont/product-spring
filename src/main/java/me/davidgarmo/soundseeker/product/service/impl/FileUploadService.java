package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class FileUploadService implements IFileUploadService {
    @Override
    public void init() {

    }

    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        return Map.of();
    }

    @Override
    public byte[] serveFile(String fileName) {
        return new byte[0];
    }

    @Override
    public String getFileContentType(String fileName) {
        return "";
    }
}
