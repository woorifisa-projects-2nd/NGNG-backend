package com.ngng.api.user.service;

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
import org.springframework.stereotype.Service;

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

    public UserMyPageReadResponseDTO readUserMyPage(Long userId){
        User user =  this.findById(userId);
        PointHistory point = pointHistoryService.readCostByUser(user);

        List<ReadProductMypageResponseDTO> sellList = productService.readSellProductsByUserId(user.getUserId()).stream()
                .map(item -> new ReadProductMypageResponseDTO().from(item))
                .collect(Collectors.toList());

        List<ReadTransactionDetailsDTO> buyList = transactionDetailsService.readAllByConsumerId(user.getUserId());

        return new UserMyPageReadResponseDTO().from(user,point,sellList,buyList);


    }

    public UserReadResponseDTO update(Long id, UserUpdateRequestDTO user){
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){
            User existingUser = userOptional.get();

            // 반복문을 사용하여 null 값이 아닌 필드만 변경
            for (Field field : User.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(user);
                    if (value != null) {
                        field.set(existingUser, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            userRepository.save(existingUser);
            return new UserReadResponseDTO().from(existingUser);
        }
        return null;
    }



    //    나중에 조인에서 구현하세요.
    public User save(User user) {
        User newUser = userRepository.save(user);
        pointHistoryService.createInitByUser(newUser);

        return newUser;
    }
}
