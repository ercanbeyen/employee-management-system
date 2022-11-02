package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.entity.Image;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(String fileName);
}
