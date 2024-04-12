package com.ngng.api.productImage.controller;

import com.ngng.api.productImage.service.AwsS3Service;
import com.ngng.api.productImage.service.CompressService;
import com.ngng.api.productImage.service.ProductImageService;
import com.ngng.api.productImage.service.UploadService;
import com.ngng.api.thumbnail.service.ThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductImageController {

    private final CompressService compressService;
    private final AwsS3Service awsS3Service;
    private final UploadService uploadService;
    private final ProductImageService productImageService;
    private final ThumbnailService thumbnailService;


    @PostMapping("/upload")
    public String fileUpload(@RequestParam("files") MultipartFile[] files , @RequestParam("productId") String productId ){

        System.out.println(productId);

        for (int i=0; i < files.length; i++ ){

            MultipartFile file = files[i];

            try {
//                1. MultipartFile to buffierIamge
                String imageUrl = uploadService.uploadImageFile(file);
                productImageService.create(Long.parseLong(productId),imageUrl);

//                썸네일 작업
                if(i == 0){
                    String thumbnails3Url=uploadService.uploadImageThumbnails(file);
                    thumbnailService.create(Long.parseLong(productId),thumbnails3Url);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "hihi";
    }
}
