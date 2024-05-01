package com.ngng.api.point.controller;

import com.ngng.api.point.dto.CheckPointRequestDTO;
import com.ngng.api.point.dto.CreateAddPointRequestDTO;
import com.ngng.api.point.dto.PaymentPointRequestDto;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pointHistoryService.updateCost(dto));
    }


    @PostMapping("/payment")
    public ResponseEntity<PointHistory> payProduct(@RequestBody PaymentPointRequestDto paymentPointRequestDto){

        return ResponseEntity.ok().body(pointHistoryService.payment(paymentPointRequestDto));
    }


    @PostMapping("/check")
    public ResponseEntity<Boolean> checkPoint(@RequestBody CheckPointRequestDTO checkPointRequestDTO) {
        return ResponseEntity.ok().body(pointHistoryService.isPaymnet(checkPointRequestDTO.getPaymentCost()));
    }


}
