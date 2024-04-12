package com.ngng.api.penalty.controller;

import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyResponseDTO;
import com.ngng.api.penalty.service.PenaltyService;
import com.ngng.api.penalty.service.PenaltyTran;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/penalties")
@RequiredArgsConstructor
@Slf4j
public class AdminPenaltyController {
    private final PenaltyService penaltyService;

    private final PenaltyTran penaltyTran;


    @GetMapping
    public ResponseEntity<List<ReadPenaltyListResponseDTO>> readAll() {
        List<ReadPenaltyListResponseDTO> reports = penaltyService.findAll();

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReadPenaltyResponseDTO> read(@PathVariable Long reportId) {
        ReadPenaltyResponseDTO readPenaltyResponseDTO = penaltyService.findById(reportId);

        return ResponseEntity.ok().body(readPenaltyResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CreatePenaltyResponseDTO> create(@RequestBody CreatePenaltyRequestDTO createPenaltyRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(penaltyTran.penaltySaveAndReportUpdate(createPenaltyRequestDTO));
    }

}
