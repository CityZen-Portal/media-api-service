package com.example.image.repository;



import com.example.image.entity.ImageData;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageData, String> {
	Optional<ImageData> findImageByName(String name);


}
