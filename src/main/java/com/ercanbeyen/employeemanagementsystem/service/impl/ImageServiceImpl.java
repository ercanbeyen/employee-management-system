package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.entity.Image;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.ImageRepository;
import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import com.ercanbeyen.employeemanagementsystem.service.ImageService;
import com.ercanbeyen.employeemanagementsystem.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        log.debug("Upload Image operation is continuing");

        Image image = imageRepository.save(
                Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .data(ImageUtils.compressImage(file.getBytes()))
                        .build()
        );

        log.debug("Image is uploaded");

        String loggedIn_email = authenticationService.getEmail();
        image.setLatestChangeBy(loggedIn_email);
        image.setLatestChangeAt(new Date());

        return image;

    }

    @Override
    public byte[] downloadImage(String fileName) {
         Image dbImage = imageRepository.findByName(fileName).orElseThrow(
                 () -> new DataNotFound("Image called " + fileName + " is not found")
         );

         log.debug("Image is found in the database, so decompressing is started");

        return ImageUtils.decompressImage(dbImage.getData());
    }
}
