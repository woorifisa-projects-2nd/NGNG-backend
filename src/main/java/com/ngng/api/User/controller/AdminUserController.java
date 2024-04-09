package com.ngng.api.User.controller;

import com.ngng.api.User.dto.*;
import com.ngng.api.User.entity.User;
import com.ngng.api.User.service.UserService;
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

    @GetMapping
    public ResponseEntity<List<ReadUserListResponseDTO>> readAll() {
        List<ReadUserListResponseDTO> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReadUserResponseDTO> read(@PathVariable Long userId) {
        ReadUserResponseDTO user = ReadUserResponseDTO.from(userService.findById(userId));

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> create(@RequestBody CreateUserRequestDTO userCreateDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userCreateDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponseDTO> update(@PathVariable Long userId, @RequestBody UpdateUserRequestDTO userUpdateDTO) {
        User found = userService.findById(userId);

        if(found == null) {
            return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.ok().body(userService.update(userId, userUpdateDTO));
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
