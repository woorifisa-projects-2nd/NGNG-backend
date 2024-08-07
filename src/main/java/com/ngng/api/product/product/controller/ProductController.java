package com.ngng.api.product.product.controller;

import com.ngng.api.product.product.dto.request.CreateProductRequestDTO;
import com.ngng.api.product.product.dto.request.UpdateProductRequestDTO;
import com.ngng.api.product.product.dto.response.ReadAllProductsDTO;
import com.ngng.api.product.product.dto.response.ReadProductResponseDTO;
import com.ngng.api.product.product.service.*;
import com.ngng.api.product.thumbnail.service.ThumbnailService;
import com.ngng.api.user.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequestMapping("/products")
@RestController
@Slf4j(topic = "product-log")
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController {
    private final ProductService productService;
    private final CompressService compressService;
    private final AwsS3Service awsS3Service;
    private final UploadService uploadService;
    private final ProductImageService productImageService;
    private final ThumbnailService thumbnailService;
    private final AuthService authService;

    @Operation(summary = "상품 추가", description = "전달받은 값으로 상품을 생성합니다.")
    @PostMapping()

    public ResponseEntity<Long> create(@RequestBody CreateProductRequestDTO product){

        Long productId = productService.create(product);
        log.info("Success Create Product id: {} Owner: {}",productId,product.getUserId());

        return ResponseEntity.created(URI.create("/products/"+productId)).body(productId);
    }

    @GetMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 상세페이지 조회", description = "id값으로 특정 상품을 찾습니다.")
    public ResponseEntity<ReadProductResponseDTO> read(@PathVariable("productId") Long productId) {
        ReadProductResponseDTO found = productService.read(productId);
        if (found == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productService.read(productId));
        }
    }

    @PostMapping("/refresh/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 게시 날짜 끌어올리기 ( 갱신 )", description = "특정 id 값으로 상품을 찾아 게시 날짜를 갱신 합니다.")
    public ResponseEntity<Long> updateRefresh(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.updateRefresh(productId));
    }

    @PostMapping("/upload")
    @Parameter(name = "files", description = "이미지 파일 배열")
    @Operation(summary = "상품 이미지 업로드", description = "s3에 상품 썸네일과 이미지들을 등록합니다.")
    public Long fileUpload(@RequestParam("files") MultipartFile[] files, @RequestParam("productId") String productId) {

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


                        //                  1. MultipartFile to buffierIamge
                        String imageUrl = uploadService.uploadImageFile(file);
                        productImageService.create(Long.parseLong(productId), imageUrl);

//                      썸네일 작업
                        if (i == 0) {
                            String thumbnails3Url = uploadService.uploadImageThumbnails(file);
                            thumbnailService.create(Long.parseLong(productId), thumbnails3Url);
                        }
                        break;
                    case "mp4": // 영상 처리
                    case "mov":
                    case "avi":
                    case "mkv":
                    case "wmv":
                    case "gif":
                        //                  1. MultipartFile to buffierIamge
                        String imageUrl3 = uploadService.uploadFile(file);
                        productImageService.create(Long.parseLong(productId), imageUrl3);
                        break;
                    default:
                        System.out.println("지원 하지 않는 파일 확장 타입 입니다.");
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return Long.parseLong(productId);
    }

    @GetMapping("")
    @Operation(summary = "상품 전체 조회", description = "..")
    public ResponseEntity<?> readAll(@RequestParam(value="page", required=false) Integer page) {

        if (page == null) {
            List<ReadAllProductsDTO> products = productService.readAll();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(products);
            }
        } else {
            Page<ReadAllProductsDTO> productsPage = productService.readAll(page);
            if (productsPage.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(productsPage);
            }
        }
    }

    @PutMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 정보 수정", description = "id값과 전달받은 값으로 특정 상품의 정보를 수정합니다.")
    public ResponseEntity<ReadProductResponseDTO> update(@PathVariable("productId") Long productId, @RequestBody UpdateProductRequestDTO request){
        return ResponseEntity.ok(productService.update(productId, request));
    }

    @DeleteMapping(path = "/{productId}")
    @Parameter(name = "id", description = "상품 id")
    @Operation(summary = "상품 삭제", description = "id값으로 해당 상품을 찾아 보이지 않게 숨깁니다.")
    public ResponseEntity<Long> updateVisibility(@PathVariable("productId") Long productId){
        Long res = productService.delete(productId);
        if(res > 0 ){
            log.info("Success Delete Product id: {}",productId);
            return  ResponseEntity.ok(res);
        }
        else {
            log.info("Fail Delete Product id: {}",productId);
            return ResponseEntity.noContent().build();
        }
    }
}
