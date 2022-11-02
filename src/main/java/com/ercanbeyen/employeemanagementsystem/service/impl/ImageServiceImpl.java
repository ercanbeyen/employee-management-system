package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.entity.Image;
import com.ercanbeyen.employeemanagementsystem.exception.ImageNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.ImageRepository;
import com.ercanbeyen.employeemanagementsystem.service.ImageService;
import com.ercanbeyen.employeemanagementsystem.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        Image image = imageRepository.save(
                Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .data(ImageUtils.compressImage(file.getBytes()))
                        .build()
        );

        image.setLatestChangeBy("Admin");
        image.setLatestChangeAt(new Date());

        return "image file called " + file.getOriginalFilename() + " is uploaded successfully";

    }

    @Override
    public byte[] downloadImage(String fileName) {
         Image dbImage = imageRepository.findByName(fileName).orElseThrow(
                 () -> new ImageNotFound("Image called " + fileName + " is not found")
         );

        return ImageUtils.decompressImage(dbImage.getData());
    }
}
