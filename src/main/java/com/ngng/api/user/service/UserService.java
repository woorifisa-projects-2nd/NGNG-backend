package com.ngng.api.user.service;

import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.product.dto.response.ReadProductMypageResponseDTO;
import com.ngng.api.product.dto.response.ReadProductResponseDTO;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.dto.UserMyPageReadResponseDTO;
import com.ngng.api.user.dto.UserReadResponseDTO;
import com.ngng.api.user.dto.UserUpdateRequestDTO;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryService pointHistoryService;
    private final TransactionDetailsService transactionDetailsService;
    private final ProductService productService;
    private final AuthService authService;


    public List<UserReadResponseDTO> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users.stream()
                .map(user -> new UserReadResponseDTO().from(user,pointHistoryService.readCostByUser(user)))
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    public UserReadResponseDTO readUserById(Long userId) {
        User user = this.findById(userId);
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user,pointHistory);
    }

    public UserReadResponseDTO readUserInfo(Long userId){
        User user = this.findById(userId);
        PointHistory point = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user,point);

    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);

    }

    public UserReadResponseDTO readUser() {
        User user =  this.authService.readAuthUser();
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user,pointHistory);
    }




    public UserMyPageReadResponseDTO readUserMyPage(){

        User user =  this.authService.readAuthUser();
        PointHistory point = pointHistoryService.readCostByUser(user);

        List<ReadProductMypageResponseDTO> sellList = productService.readSellProductsByUserId(user.getUserId()).stream()
                .map(item -> new ReadProductMypageResponseDTO().from(item))
                .collect(Collectors.toList());

        List<ReadTransactionDetailsDTO> buyList = transactionDetailsService.readAllByConsumerId(user.getUserId());

        return new UserMyPageReadResponseDTO().from(user,point,sellList,buyList);


    }

    @Transactional
    public UserReadResponseDTO update( UserUpdateRequestDTO userDto){

        User user =  this.authService.readAuthUser();

        if(user == null) return UserReadResponseDTO.builder().build();


        if(userDto.getNickname() != null) user.setNickname(userDto.getNickname());
        if(userDto.getAddress() != null) user.setAddress(userDto.getAddress());


        return new UserReadResponseDTO().from(user);

    }

}
