package com.example.image.service;


import com.example.image.entity.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface ImageService {
    ImageData saveImage(String name, MultipartFile file);
    byte[] getImageById(String id);
    List<ImageData> getAllImages();
}

