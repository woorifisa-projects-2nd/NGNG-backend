package com.ngng.api.productImage.controller;

import com.ngng.api.productImage.dto.request.DeleteImageRequestDTO;
import com.ngng.api.productImage.service.AwsS3Service;
import com.ngng.api.productImage.service.CompressService;
import com.ngng.api.productImage.service.ProductImageService;
import com.ngng.api.productImage.service.UploadService;
import com.ngng.api.thumbnail.service.ThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//            확장자 구분
            String fileName = file.getOriginalFilename();
            String[] tokens = fileName.split("\\."); // "." 문자를 기준으로 문자열을 분리합니다.
            String extension = tokens[tokens.length - 1];


        try {
                switch (extension) {
                    case "jfif": // 이미지 처리
                    case "jpg":
                    case "jpeg":
                    case "png":
                        System.out.println("이미지 처리");

    //                  1. MultipartFile to buffierIamge
                            String imageUrl = uploadService.uploadImageFile(file);
                            productImageService.create(Long.parseLong(productId),imageUrl);

//                      썸네일 작업
                        if(i == 0){
                            String thumbnails3Url=uploadService.uploadImageThumbnails(file);
                            thumbnailService.create(Long.parseLong(productId),thumbnails3Url);
                        }
                        break;
                    case "mp4": // 영상 처리
                    case "mov":
                    case "avi":
                    case "mkv":
                    case "wmv":
                    case "gif" :
                        System.out.println("영상 처리");
    //                  1. MultipartFile to buffierIamge
                            String imageUrl3 = uploadService.uploadFile(file);
                            productImageService.create(Long.parseLong(productId),imageUrl3);
                        break;
                    default:
                        System.out.println("지원 하지 않는 파일 확장 타입 입니다.");
                        break;
                 }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return "hihi";
    }

    @DeleteMapping("deleteImage/{productId}")
    public ResponseEntity<Long> deleteImage(@PathVariable("productId") Long productId, @RequestBody DeleteImageRequestDTO deleteImageRequestDTO) {
        System.out.println("productImageId = " + productId);
        System.out.println("deleteImageRequestDTO = " + deleteImageRequestDTO.getImageURL());
        return ResponseEntity.ok(productImageService.delete(productId, deleteImageRequestDTO.getImageURL()));
    }
}
