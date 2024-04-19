package com.ngng.api.point.controller;

import com.ngng.api.global.security.jwt.util.JwtTokenVerifier;
import com.ngng.api.point.dto.CreateAddPointRequestDTO;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
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
    private final JwtTokenVerifier jwtTokenVerifier;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PointHistory> readPoint(@RequestHeader("Authorization") String token){
        User user = userRepository.findUserByEmail(jwtTokenVerifier.getEmail(token)).orElse(null);

        return ResponseEntity.ok().body(pointHistoryService.readCostByUser(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PointHistory>> readAllPointHistory(@RequestHeader("Authorization") String token){

        User user = userRepository.findUserByEmail(jwtTokenVerifier.getEmail(token)).orElse(null);

        return ResponseEntity.ok().body(pointHistoryService.readPointHistories(user));
    }


    @PostMapping("")
    public ResponseEntity<PointHistory> addPoint(@RequestHeader("Authorization") String token,@RequestBody @Valid final CreateAddPointRequestDTO dto){
        User user = userRepository.findUserByEmail(jwtTokenVerifier.getEmail(token)).orElse(null);

        return ResponseEntity.ok().body(pointHistoryService.updateCostByUserAndRequest(user,dto));
    }
}
