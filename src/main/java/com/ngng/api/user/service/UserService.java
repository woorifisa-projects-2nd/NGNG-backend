package com.ngng.api.user.service;

import com.ngng.api.global.security.jwt.custom.CustomUserDetails;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.service.PointHistoryService;
import com.ngng.api.product.dto.response.ReadProductMypageResponseDTO;
import com.ngng.api.product.service.ProductService;
import com.ngng.api.transaction.dto.ReadTransactionDetailsDTO;
import com.ngng.api.transaction.service.TransactionDetailsService;
import com.ngng.api.user.dto.UserMyPageReadResponseDTO;
import com.ngng.api.user.dto.UserReadResponseDTO;
import com.ngng.api.user.dto.UserUpdateRequestDTO;
import com.ngng.api.user.dto.request.AdminUserUpdateRequest;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointHistoryService pointHistoryService;
    private final TransactionDetailsService transactionDetailsService;
    private final ProductService productService;
    private final PasswordEncoder bCryptPasswordEncoder;


    public List<UserReadResponseDTO> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users.stream()
                .map(user -> new UserReadResponseDTO().from(user, pointHistoryService.readCostByUser(user)))
                .collect(Collectors.toList());
    }

    public Page<UserReadResponseDTO> findAll(Integer page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("userId"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));

        Page<User> users = userRepository.findByVisible(true, pageable);

        return users.map(user ->
                new UserReadResponseDTO().from(user, pointHistoryService.readCostByUser(user))
        );
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    public UserReadResponseDTO readUserById(Long userId) {
        User user = this.findById(userId);
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user, pointHistory);
    }

    public UserReadResponseDTO readUserInfo(Long userId) {
        User user = this.findById(userId);
        PointHistory point = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user, point);

    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);

    }

    public UserReadResponseDTO readUser() {
        User user = this.readAuthUser();
        PointHistory pointHistory = pointHistoryService.readCostByUser(user);

        return new UserReadResponseDTO().from(user, pointHistory);
    }


    public UserMyPageReadResponseDTO readUserMyPage() {

        User user = this.readAuthUser();
        PointHistory point = pointHistoryService.readCostByUser(user);

        List<ReadProductMypageResponseDTO> sellList = productService.readSellProductsByUserId(user.getUserId()).stream()
                .map(item -> new ReadProductMypageResponseDTO().from(item))
                .collect(Collectors.toList());

        List<ReadTransactionDetailsDTO> buyList = transactionDetailsService.readAllByConsumerId(user.getUserId());

        return new UserMyPageReadResponseDTO().from(user, point, sellList, buyList);


    }

    @Transactional
    public UserReadResponseDTO update(UserUpdateRequestDTO userDto) {

        User user = this.readAuthUser();

        if (user == null) return UserReadResponseDTO.builder().build();


        if (userDto.getNickname() != null) user.setNickname(userDto.getNickname());
        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());


        return new UserReadResponseDTO().from(user);

    }

    public User readAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        String email = details.getUser().getEmail();

        return this.userRepository.findUserByEmail(email).orElse(null);
    }

    public Long delete(Long userId){
        User user = userRepository.findById(userId).orElseThrow();

        user.setVisible(false);

        return userRepository.save(user).getUserId();

    }

    @Transactional
    public UserReadResponseDTO adminUserUpdate(AdminUserUpdateRequest userUpdateDTO) {
        User user = this.readAuthUser();

        if (user == null) return UserReadResponseDTO.builder().build();

        if (userUpdateDTO.getName() != null) user.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getNickName() != null) user.setNickname(userUpdateDTO.getNickName());
        if (userUpdateDTO.getPhoneNumber() != null) user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        if (userUpdateDTO.getEmail() != null) user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getPassword() != null) user.setPassword(bCryptPasswordEncoder.encode(userUpdateDTO.getPassword()));
        if (userUpdateDTO.getAccountBank() != null) user.setAccountBank(userUpdateDTO.getAccountBank());
        if (userUpdateDTO.getAccountNumber() != null) user.setAccountNumber(userUpdateDTO.getAccountNumber());
        if (userUpdateDTO.getAddress() != null) user.setAddress(userUpdateDTO.getAddress());

        return new UserReadResponseDTO().from(user);
    }
}
