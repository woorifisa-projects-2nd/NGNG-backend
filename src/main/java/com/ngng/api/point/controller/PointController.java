package com.ngng.api.point.controller;

import com.ngng.api.point.dto.CreateAddPointRequestDTO;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.user.entity.User;
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


    @GetMapping("/{id}")
    public ResponseEntity<PointHistory> readPoint(@PathVariable("id") User user){
        return ResponseEntity.ok().body(pointHistoryService.readCostByUser(user));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<PointHistory>> readAllPointHistory(@PathVariable("id") User user){
        return ResponseEntity.ok().body(pointHistoryService.readPointHistories(user));
    }


    @PostMapping("/{id}")
    public ResponseEntity<PointHistory> addPoint(@PathVariable("id") User user,@RequestBody @Valid final CreateAddPointRequestDTO dto){
        return ResponseEntity.ok().body(pointHistoryService.updateCostByUserAndRequest(user,dto));
    }
}
