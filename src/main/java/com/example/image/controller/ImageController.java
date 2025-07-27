package com.example.image.controller;

import com.example.image.entity.ImageData;
import com.example.image.enumeration.ResponseStatus;
import com.example.image.response.CommonResponse;
import com.example.image.response.ImageResponse;
import com.example.image.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadImage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MultipartFile imageFile) {

        CommonResponse commonResponse = new CommonResponse();

        if (name == null || name.trim().isEmpty()) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Name must not be empty");
            commonResponse.setStatusCode(400);
            commonResponse.setData(null);
            return ResponseEntity.badRequest().body(commonResponse);
        }

        if (imageFile == null || imageFile.isEmpty()) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Image file must not be empty");
            commonResponse.setStatusCode(400);
            commonResponse.setData(null);
            return ResponseEntity.badRequest().body(commonResponse);
        }

        try {
            ImageData imageData = imageService.saveImage(name, imageFile);
            ImageResponse imageResponse = new ImageResponse(
                    imageData.getId(),
                    imageData.getName(),
                    imageData.getImagePath()
            );

            commonResponse.setStatus(ResponseStatus.SUCCESS);
            commonResponse.setMessage("Image uploaded successfully");
            commonResponse.setStatusCode(200);
            commonResponse.setData(imageResponse);
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Failed to upload image: " + e.getMessage());
            commonResponse.setStatusCode(500);
            commonResponse.setData(null);
            return ResponseEntity.status(500).body(commonResponse);
        }
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<CommonResponse> uploadMultipleImages(
            @RequestParam(required = false) String name,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles) {

        CommonResponse commonResponse = new CommonResponse();

        if (name == null || name.trim().isEmpty()) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Name must not be empty");
            commonResponse.setStatusCode(400);
            commonResponse.setData(null);
            return ResponseEntity.badRequest().body(commonResponse);
        }

        if (imageFiles == null || imageFiles.isEmpty()) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("At least one image file must be provided");
            commonResponse.setStatusCode(400);
            commonResponse.setData(null);
            return ResponseEntity.badRequest().body(commonResponse);
        }

        try {
            List<ImageResponse> responses = imageFiles.stream()
                    .filter(file -> !file.isEmpty())
                    .map(file -> {
                        try {
                            ImageData saved = imageService.saveImage(name, file);
                            return new ImageResponse(
                                    saved.getId(),
                                    saved.getName(),
                                    saved.getImagePath()
                            );
                        } catch (Exception e) {
                            throw new RuntimeException("Error saving image: " + file.getOriginalFilename(), e);
                        }
                    })
                    .toList();

            commonResponse.setStatus(ResponseStatus.SUCCESS);
            commonResponse.setMessage("Images uploaded successfully");
            commonResponse.setStatusCode(200);
            commonResponse.setData(responses);
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Failed to upload images: " + e.getMessage());
            commonResponse.setStatusCode(500);
            commonResponse.setData(null);
            return ResponseEntity.status(500).body(commonResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getImage(@PathVariable String id) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            ImageData _image = imageService.getImageById(id);
            ImageResponse imageResponse=new ImageResponse();
            imageResponse.setId(_image.getId());
            imageResponse.setName(_image.getName());
            imageResponse.setPath(_image.getImagePath());
            commonResponse.setStatus(ResponseStatus.SUCCESS);
            commonResponse.setMessage("Image fetched successfully");
            commonResponse.setStatusCode(200);
            commonResponse.setData(imageResponse);
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatus(ResponseStatus.NOT_FOUND);
            commonResponse.setMessage("Image not found with ID: " + e.getMessage());
            commonResponse.setStatusCode(404);
            commonResponse.setData(null);
            return ResponseEntity.status(404).body(commonResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllImages() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<ImageData> images = imageService.getAllImages();
            commonResponse.setStatus(ResponseStatus.SUCCESS);
            commonResponse.setMessage("Fetched all images successfully");
            commonResponse.setStatusCode(200);
            commonResponse.setData(images);
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatus(ResponseStatus.ERROR);
            commonResponse.setMessage("Failed to fetch images: " + e.getMessage());
            commonResponse.setStatusCode(500);
            commonResponse.setData(null);
            return ResponseEntity.status(500).body(commonResponse);
        }
    }
    
    @GetMapping("/get/{name}")
    public ResponseEntity<CommonResponse> getImagebyName(@PathVariable String name) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            ImageData _image = imageService.getImageByName(name);
            ImageResponse imageResponse=new ImageResponse();
            imageResponse.setId(_image.getId());
            imageResponse.setName(_image.getName());
            imageResponse.setPath(_image.getImagePath());
            commonResponse.setStatus(ResponseStatus.SUCCESS);
            commonResponse.setMessage("Image fetched successfully");
            commonResponse.setStatusCode(200);
            commonResponse.setData(imageResponse);
            return ResponseEntity.ok(commonResponse);
        } catch (Exception e) {
            commonResponse.setStatus(ResponseStatus.NOT_FOUND);
            commonResponse.setMessage("Image not found with ID: " + e.getMessage());
            commonResponse.setStatusCode(404);
            commonResponse.setData(null);
            return ResponseEntity.status(404).body(commonResponse);
        }
    }
}
