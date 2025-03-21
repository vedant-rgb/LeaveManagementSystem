package com.leaveManagement.PictLeaveProcessing.Configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dyrmahn2b", // Replace with your Cloudinary cloud name
                "api_key", "336921185161497",     // Replace with your Cloudinary API key
                "api_secret", "SQTafXOjKIhWf3JhnG90Yl7djUs" // Replace with your Cloudinary API secret
        ));
    }
}