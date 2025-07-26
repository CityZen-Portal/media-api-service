package com.example.image.service.impl;


import com.example.image.entity.ImageData;
import com.example.image.repository.ImageRepository;
import com.example.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageData saveImage(String name, MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, file.getBytes());

            ImageData image = new ImageData();
            image.setName(name);
            image.setImagePath(filePath.toString());

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    @Override
    public byte[] getImageById(String id) {
        ImageData image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + id));
        try {
            Path path = Paths.get(image.getImagePath());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not read image: " + e.getMessage());
        }
    }

    @Override
    public List<ImageData> getAllImages() {
        return imageRepository.findAll();
    }
}
