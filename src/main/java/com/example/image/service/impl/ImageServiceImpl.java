package com.example.image.service.impl;


import com.example.image.entity.ImageData;
import com.example.image.exception.BadRequestException;
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
import java.util.*;

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
    public ImageData getImageById(String id) {
           Optional<ImageData> image = imageRepository.findById(id);
        if(image.isPresent())
        {
            return image.get();
        }
        else{
            throw new BadRequestException("Image not found");
        }
    }
    @Override
    public ImageData getImageByName(String name) {
           Optional<ImageData> image = imageRepository.findImageByName(name);
        if(image.isEmpty())
        {
        	throw new BadRequestException("Image not found");
        }
        else{
        	return image.get();
        }
    }
    @Override
    public List<ImageData> getAllImages() {
        return imageRepository.findAll();
    }
}
