package com.ngng.api.report.reportImage.controller;


import com.ngng.api.product.product.service.AwsS3Service;
import com.ngng.api.report.report.service.ReportImageService;
import com.ngng.api.report.report.service.ReportImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReportImageController {

    private final AwsS3Service awsS3Service;
    private final ReportImageUploadService uploadService;
    private final ReportImageService reportImageService;


    @PostMapping("/reportImages/upload")
    public String fileUpload(@RequestParam("files") MultipartFile[] files, @RequestParam("reportId") String reportId) {

        System.out.println(reportId);

        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];

//            확장자 구분
            String fileName = file.getOriginalFilename();
            String[] tokens = fileName.split("\\."); // "." 문자를 기준으로 문자열을 분리합니다.
            String extension = tokens[tokens.length - 1].toLowerCase();


            try {
                switch (extension) {
                    case "jfif": // 이미지 처리
                    case "jpg":
                    case "jpeg":
                    case "png":
                        System.out.println("이미지 처리");

                        // 1. MultipartFile to buffierIamge
                        String imageUrl = uploadService.uploadImageFile(file);
                        reportImageService.create(Long.parseLong(reportId), imageUrl);

                        break;
                    default:
                        System.out.println("지원 하지 않는 파일 확장 타입 입니다.");
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return "reportImages upload";
    }

}
