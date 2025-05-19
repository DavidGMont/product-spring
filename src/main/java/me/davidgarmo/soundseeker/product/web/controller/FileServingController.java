package me.davidgarmo.soundseeker.product.web.controller;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/uploads")
public class FileServingController {
    private final IFileUploadService fileUploadService;

    public FileServingController(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String fileName) {
        byte[] fileContent = fileUploadService.serveFile(fileName);
        String contentType = fileUploadService.getFileContentType(fileName);
        String eTag = Integer.toHexString(Arrays.hashCode(fileContent));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setETag(eTag);

        CacheControl cacheControl = CacheControl.maxAge(365, TimeUnit.DAYS)
                .cachePublic();

        return ResponseEntity.ok()
                .headers(headers)
                .cacheControl(cacheControl)
                .body(fileContent);
    }
}
