package com.example.image.repository;



import com.example.image.entity.ImageData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageData, String> {
}
