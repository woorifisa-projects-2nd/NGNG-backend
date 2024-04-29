package com.ngng.api.report.controller;

import com.ngng.api.report.dto.CreateReportRequestDTO;
import com.ngng.api.report.dto.CreateReportResponseDTO;
import com.ngng.api.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Slf4j
public class CreateReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<CreateReportResponseDTO> create(@RequestBody CreateReportRequestDTO createReportRequestDTO) {
        System.out.println("ddddddddddddd");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportService.save(createReportRequestDTO));
    }

}
