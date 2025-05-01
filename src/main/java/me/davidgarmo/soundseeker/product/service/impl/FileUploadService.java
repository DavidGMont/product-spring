package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUploadService implements IFileUploadService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(
            Arrays.asList("gif", "jpg", "jpeg", "png", "webp"));

    private static final Set<String> ALLOWED_MIME_TYPES = new HashSet<>(
            Arrays.asList(
                    "image/gif",
                    "image/jpeg",
                    "image/png",
                    "image/webp"));

    private static final Map<String, byte[][]> FILE_SIGNATURES = new HashMap<>();

    static {
        FILE_SIGNATURES.put("gif", new byte[][]{
                {(byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x37, (byte) 0x61},
                {(byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x39, (byte) 0x61}
        });

        FILE_SIGNATURES.put("jpg", new byte[][]{
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0},
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1},
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE2},
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE3},
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xEE},
                {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xDB}
        });

        FILE_SIGNATURES.put("jpeg", FILE_SIGNATURES.get("jpg"));

        FILE_SIGNATURES.put("png", new byte[][]{
                {(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47}}
        );

        FILE_SIGNATURES.put("webp", new byte[][]{
                {(byte) 0x52, (byte) 0x49, (byte) 0x46, (byte) 0x46, 0, 0, 0, 0, (byte) 0x57, (byte) 0x45, (byte) 0x42, (byte) 0x50}
        });
    }

    @Value("${file.upload.directory:uploads}")
    private String uploadDir;

    @Override
    public void init() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                LOGGER.debug("Upload directory created at: {}", uploadPath.toAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
