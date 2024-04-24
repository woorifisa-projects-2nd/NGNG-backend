package com.ngng.api.point.controller;

import com.ngng.api.point.dto.CreateAddPointRequestDTO;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {
    private final PointHistoryService pointHistoryService;


    @GetMapping("")
    public ResponseEntity<PointHistory> readPoint(){
        return ResponseEntity.ok().body(pointHistoryService.readCost());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PointHistory>> readAllPointHistory(){
        return ResponseEntity.ok().body(pointHistoryService.readPointHistories());
    }


    @PostMapping("")
    public ResponseEntity<PointHistory> addPoint(@RequestBody @Valid final CreateAddPointRequestDTO dto){
        return ResponseEntity.ok().body(pointHistoryService.updateCost(dto));
    }
}
