package de.csiem.backend.controller;

import de.csiem.backend.model.DetectedText;
import de.csiem.backend.service.AwsImageUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/image/upload")
public class AwsImageUploadController {

    private final AwsImageUploadService awsImageUploadService;

    public AwsImageUploadController(AwsImageUploadService awsImageUploadService) {
        this.awsImageUploadService = awsImageUploadService;
    }


    @PostMapping
    public DetectedText uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        return awsImageUploadService.uploadImage(file);
    }
}
