package com.ngng.api.user.user.controller;

import com.ngng.api.user.point.entity.PointHistory;
import com.ngng.api.user.point.service.PointHistoryService;
import com.ngng.api.user.user.dto.UserReadResponseDTO;
import com.ngng.api.user.user.dto.request.AdminUserUpdateRequest;
import com.ngng.api.user.user.entity.User;
import com.ngng.api.user.user.service.JoinService;
import com.ngng.api.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final JoinService joinService;

    @GetMapping
    public ResponseEntity<Page<UserReadResponseDTO>> readAll(@RequestParam(value="page", defaultValue="0") Integer page) {

        Page<UserReadResponseDTO> usersPage = userService.findAll(page);
        return ResponseEntity.ok(usersPage);
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
    public ResponseEntity<UserReadResponseDTO> update(@PathVariable Long userId, @RequestBody AdminUserUpdateRequest userUpdateDTO) {
        User found = userService.findById(userId);

        if(found == null) {
            return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.ok().body(userService.adminUserUpdate(userUpdateDTO));
        }
    }


    // 사용자 삭제(visible 여부)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> delete(@PathVariable Long userId) {
//        User found = userService.findById(userId);
        return ResponseEntity.ok(userService.delete(userId));

    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }

}
