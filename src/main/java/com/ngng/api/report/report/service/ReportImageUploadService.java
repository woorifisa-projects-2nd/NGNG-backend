package com.ngng.api.report.report.service;

import com.ngng.api.product.product.service.AwsS3Service;
import com.ngng.api.product.product.service.CompressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReportImageUploadService {

    private final CompressService compressService;
    private final AwsS3Service awsS3Service;


    public String uploadFile(MultipartFile file) throws IOException {
        //   이미지 주소
        return awsS3Service.saveFile(file);


    }
    public String uploadImageFile(MultipartFile file) throws IOException {

        BufferedImage image = ImageIO.read(file.getInputStream());

        BufferedImage resizeImage = compressService.combineWaterMark(image); // 이것도 비율 조절 됨

        MultipartFile uploadFile = compressService.convertBufferedImageToMultipartFile(resizeImage,file);

        //   이미지 주소
        return awsS3Service.saveFile(uploadFile);


    }
    public String uploadImageThumbnails(MultipartFile file) throws IOException{

        BufferedImage image = ImageIO.read(file.getInputStream());

        BufferedImage thumbnailsResizeImage = compressService.combineThumbnailWaterMark(image); // 이것도 비율 조절 됨

        MultipartFile thumbnailsUploadFile = compressService.convertBufferedImageToMultipartFile(thumbnailsResizeImage,file);

//   이미지 주소
        return awsS3Service.saveFile(thumbnailsUploadFile);


    }
}
