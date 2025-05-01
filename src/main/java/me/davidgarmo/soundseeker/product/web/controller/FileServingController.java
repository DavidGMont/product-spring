package me.davidgarmo.soundseeker.product.web.controller;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploads")
@CrossOrigin
public class FileServingController {
    private final IFileUploadService fileUploadService;

    public FileServingController(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
