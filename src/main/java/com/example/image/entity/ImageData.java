package com.example.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {

    @Id
    private String id;
    private String name;
    private String imagePath;

}

