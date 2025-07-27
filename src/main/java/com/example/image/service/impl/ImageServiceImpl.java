package com.example.image.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.image.entity.ImageData;
import com.example.image.exception.BadRequestException;
import com.example.image.repository.ImageRepository;
import com.example.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ImageData saveImage(String name, MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("secure_url").toString();

            ImageData image = new ImageData();
            image.setName(name);
            image.setImagePath(url);

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Cloudinary upload failed: " + e.getMessage());
        }
    }

    @Override
    public ImageData getImageById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Image not found"));
    }

    @Override
    public ImageData getImageByName(String name) {
        return imageRepository.findImageByName(name)
                .orElseThrow(() -> new BadRequestException("Image not found"));
    }

    @Override
    public List<ImageData> getAllImages() {
        return imageRepository.findAll();
    }
}
