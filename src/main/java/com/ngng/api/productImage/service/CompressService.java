package com.ngng.api.productImage.service;


import com.ngng.api.productImage.utils.CustomMultipartFile;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class CompressService {
//    https://wildeveloperetrain.tistory.com/289

    @SneakyThrows
    public BufferedImage combineWaterMark(BufferedImage originalImage) {
        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        // 백의 자리 숫자 추출
        int hundredsDigit = (originHeight / 100) % 10;

        int newHeight = (hundredsDigit == 0 ? 1: hundredsDigit) * 100;

//        이미지 비율
        double  aspectRatio = (double) originWidth / originHeight;
        int newWidth = (int) Math.round(newHeight * aspectRatio);

        return Thumbnails.of(originalImage)
                .size(newWidth, newHeight)
                .watermark(Positions.TOP_CENTER, ImageIO.read(new File("./images/water/ngng-watermark_x"+newHeight+".png")), 0.5f)
                .outputQuality(0.8)
                .asBufferedImage();
    }

    @SneakyThrows
    public BufferedImage combineThumbnailWaterMark(BufferedImage originalImage) {
        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        int newHeight = 100;

        //        이미지 비율
        double  aspectRatio = (double) originWidth / originHeight;
        int newWidth = (int) Math.round(newHeight * aspectRatio);


//        워터마크 씌운 이미지
        return Thumbnails.of(originalImage)
                .size(newWidth, newHeight)
                .watermark(Positions.TOP_CENTER, ImageIO.read(new File("./images/water/ngng-watermark_x"+newHeight+".png")), 0.5f)
                .outputQuality(0.8)
                .asBufferedImage();
    }


    public MultipartFile convertBufferedImageToMultipartFile(BufferedImage image, MultipartFile originFile) {


        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpeg", out);

        } catch (IOException e) {

            return null;
        }

        byte[] bytes = out.toByteArray();


        return new CustomMultipartFile(bytes, originFile.getName(), originFile.getOriginalFilename(), originFile.getContentType(), bytes.length );
    }
}
