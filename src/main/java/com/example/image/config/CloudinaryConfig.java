package com.example.image.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dk75r8sim",
                "api_key", "527738338342332",
                "api_secret", "GRadBQdyBd9NHWBXbTQpr6hqpNM"));
    }
}
