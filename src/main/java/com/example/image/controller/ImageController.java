package com.example.image.controller;

import com.example.image.entity.ImageData;
import com.example.image.enumeration.ResponseStatus;
import com.example.image.response.CommonResponse;
import com.example.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String home() {
        return "home";
    }

    // Single Image Upload
    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadImage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MultipartFile imageFile) {

        if (name == null || name.trim().isEmpty()) {
            CommonResponse error = new CommonResponse();
            error.setMessage("Name must not be empty");
            error.setStatus(ResponseStatus.ERROR);
            error.setData(null);
            error.setStatusCode(400);
            return ResponseEntity.badRequest().body(error);
        }

        if (imageFile == null || imageFile.isEmpty()) {
            CommonResponse error = new CommonResponse();
            error.setMessage("Image file must not be empty");
            error.setStatus(ResponseStatus.ERROR);
            error.setData(null);
            error.setStatusCode(400);
            return ResponseEntity.badRequest().body(error);
        }

        ImageData result = imageService.saveImage(name, imageFile);
        CommonResponse success = new CommonResponse();
        success.setMessage("Image uploaded successfully");
        success.setStatus(ResponseStatus.SUCCESS);
        success.setData(result);
        success.setStatusCode(200);
        return ResponseEntity.ok(success);
    }

    // ✅ New: Multiple Image Upload
    @PostMapping("/upload/multiple")
    public ResponseEntity<CommonResponse> uploadMultipleImages(
            @RequestParam String name,
            @RequestParam MultipartFile[] files) {

        if (files == null || files.length == 0) {
            CommonResponse error = new CommonResponse();
            error.setMessage("No files provided");
            error.setStatus(ResponseStatus.ERROR);
            error.setData(null);
            error.setStatusCode(400);
            return ResponseEntity.badRequest().body(error);
        }

        List<ImageData> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                ImageData imageData = imageService.saveImage(name, file);
                savedImages.add(imageData);
            }
        }

        CommonResponse success = new CommonResponse();
        success.setMessage("All images uploaded successfully");
        success.setStatus(ResponseStatus.SUCCESS);
        success.setData(savedImages);
        success.setStatusCode(200);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        byte[] imageBytes = imageService.getImageById(id);
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(imageBytes);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageData>> getAllImages() {
        List<ImageData> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }
}
