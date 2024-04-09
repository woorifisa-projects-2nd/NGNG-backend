package com.ngng.api.product.dto;

import com.ngng.api.User.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String nickname;

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
    }
}
