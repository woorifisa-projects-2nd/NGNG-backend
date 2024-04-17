package com.ngng.api.user.controller;

import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.user.dto.*;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.service.JoinService;
import com.ngng.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final JoinService joinService;

    @GetMapping
    public ResponseEntity<List<UserReadResponseDTO>> readAll() {
        List<UserReadResponseDTO> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserReadResponseDTO> read(@PathVariable Long userId) {
        User user = userService.findById(userId);
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);


        UserReadResponseDTO response = new UserReadResponseDTO().from(userService.findById(userId),pointHistory);


        return ResponseEntity.ok().body(response);
    }

//    @PostMapping
//    public ResponseEntity<CreateUserResponseDTO> create(@RequestBody CreateUserRequestDTO userCreateDTO) {

//        추후 JoionServer에서 로그인 구현 할애 할거 같음

//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(userService.save(userCreateDTO));
//    return null;

//    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserReadResponseDTO> update(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO userUpdateDTO) {
        User found = userService.findById(userId);

        if(found == null) {
            return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.ok().body(userService.update(userUpdateDTO));
        }
    }


    // 사용자 삭제(visible 여부)
//    @PatchMapping("/{userId}")
//    public ResponseEntity<DeleteUserResponseDTO> delete(@PathVariable Long userId) {
//        User found = userService.findById(userId);
//
//
//    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }

}
