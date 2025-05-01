package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import me.davidgarmo.soundseeker.product.service.exception.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        } catch (IOException e) {
            throw new FileUploadException("Failed to initialize upload directory.", e);
        }
    }

    @Override
    public Map<String, String> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("No file uploaded, file is empty.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new FileUploadException("Invalid name, file name is empty.");
        }

        String extension = getFileExtension(originalFileName);

        if (!isValidExtension(extension)) {
            throw new FileUploadException("File type not permitted. Allowed types: " +
                    String.join(", ", ALLOWED_EXTENSIONS) + ".");
        }

        String contentType = file.getContentType();
        if (!isValidMimeType(contentType)) {
            throw new FileUploadException("File type not permitted. Invalid MIME type: " + contentType + ".");
        }

        try {
            byte[] fileStart = Arrays.copyOf(file.getBytes(), (int) Math.min(12, file.getSize()));

            if (fileStart.length < 4) {
                throw new FileUploadException("File is too small to be a valid image.");
            }

            if (!isValidFileContent(fileStart, extension)) {
                LOGGER.warn("File content does not match the expected signature for extension: {}.", extension);
                throw new FileUploadException("File content does not match with the declared extension.");
            }

            String fileName = System.currentTimeMillis() + "." + originalFileName;
            Path targetLocation = Paths.get(uploadDir).resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation);

            Map<String, String> fileInfo = new HashMap<>();
            fileInfo.put("filePath", "uploads/" + fileName);
            fileInfo.put("fileName", fileName);
            fileInfo.put("originalFileName", originalFileName);

            return fileInfo;
        } catch (IOException e) {
            throw new FileUploadException("Failed to save file: " + file.getOriginalFilename() + ".", e);
        }
    }

    @Override
    public byte[] serveFile(String fileName) {
        return new byte[0];
    }

    @Override
    public String getFileContentType(String fileName) {
        return "";
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isValidExtension(String extension) {
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    private boolean isValidMimeType(String mimeType) {
        if (mimeType == null) {
            return false;
        }
        return ALLOWED_MIME_TYPES.contains(mimeType.toLowerCase());
    }

    private boolean isValidFileContent(byte[] fileStart, String extension) {
        byte[][] signatures = FILE_SIGNATURES.get(extension.toLowerCase());
        if (signatures == null) return false;

        for (byte[] signature : signatures) {
            boolean match = true;
            if (extension.equalsIgnoreCase("webp")) {
                if (fileStart.length < 12) return false;
                if (!(fileStart[0] == signature[0] && fileStart[1] == signature[1] && fileStart[2] == signature[2] && fileStart[3] == signature[3])) {
                    continue;
                }
                if (!(fileStart[8] == signature[8] && fileStart[9] == signature[9] && fileStart[10] == signature[10] && fileStart[11] == signature[11])) {
                    continue;
                }
                return true;
            } else {
                for (int i = 0; i < signature.length; i++) {
                    if (i >= fileStart.length || fileStart[i] != signature[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
    }
}
