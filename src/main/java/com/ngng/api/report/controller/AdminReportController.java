package com.ngng.api.report.controller;

import com.ngng.api.report.dto.*;
import com.ngng.api.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
@Slf4j
public class AdminReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Page<ReadReportListResponseDTO>> readAll(@RequestParam(value="page", defaultValue="0") Integer page, @RequestParam Boolean unprocessedOnly) {
        Page<ReadReportListResponseDTO> reports = reportService.findAll(page, unprocessedOnly);

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReadReportResponseDTO> read(@PathVariable Long reportId) {
        ReadReportResponseDTO reportDTO = reportService.findById(reportId);

        return ResponseEntity.ok().body(reportDTO);
    }

    // is_report 값만 수정
    @PatchMapping("/{reportId}")
    public ResponseEntity<ReadReportResponseDTO> update(@PathVariable Long reportId) {
        ReadReportResponseDTO report = reportService.findById(reportId);

        if(report == null) {
            return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.ok().body(reportService.update(reportId, true));
        }
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<DeleteReportResponseDTO> delete(@PathVariable Long reportId) {
        return ResponseEntity.ok()
                .body(reportService.delete(reportId));

    }



    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }
}
