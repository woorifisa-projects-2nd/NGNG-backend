package com.ngng.api.Penalty.controller;

import com.ngng.api.Penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.Penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.Penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.Penalty.dto.ReadPenaltyResponseDTO;
import com.ngng.api.Penalty.service.PenaltyService;
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


    @GetMapping
    public ResponseEntity<List<ReadPenaltyListResponseDTO>> readAll() {
        List<ReadPenaltyListResponseDTO> reports = penaltyService.findAll();

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{penaltyId}")
    public ResponseEntity<ReadPenaltyResponseDTO> read(@PathVariable Long penaltyId) {
        ReadPenaltyResponseDTO readPenaltyResponseDTO = penaltyService.findById(penaltyId);

        return ResponseEntity.ok().body(readPenaltyResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CreatePenaltyResponseDTO> create(@RequestBody CreatePenaltyRequestDTO createPenaltyRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(penaltyService.save(createPenaltyRequestDTO));
    }

}
