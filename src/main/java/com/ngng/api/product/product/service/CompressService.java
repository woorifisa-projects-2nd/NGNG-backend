package com.ngng.api.product.product.service;


import com.ngng.api.product.product.utils.CustomMultipartFile;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class CompressService {
//    https://wildeveloperetrain.tistory.com/289

    @SneakyThrows
    public BufferedImage combineWaterMark(BufferedImage originalImage) {
        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        // 백의 자리 숫자 추출
        int hundredsDigit = (originHeight / 100) % 10;

        int newHeight = (hundredsDigit > 8 ? 9: hundredsDigit + 1) * 100;

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

        int newHeight = 200;

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
        String fileName = originFile.getOriginalFilename();
        String[] tokens = fileName.split("\\."); // "." 문자를 기준으로 문자열을 분리합니다.
        String extension = tokens[tokens.length - 1].toLowerCase();

        try {
            if(extension.equals("png")) ImageIO.write(image, "png", out);
            else ImageIO.write(image, "jpeg", out);

        } catch (IOException e) {

            return null;
        }
        byte[] bytes = out.toByteArray();


        return new CustomMultipartFile(bytes, originFile.getName(), originFile.getOriginalFilename(), originFile.getContentType(), bytes.length );
    }
}
