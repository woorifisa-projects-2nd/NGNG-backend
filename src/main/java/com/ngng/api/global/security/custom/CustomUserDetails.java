package com.ngng.api.global.security.custom;

import com.ngng.api.user.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final String ROLE_PREFIX = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        // spring security에서 authority를 처리할 때 ROLE_ 접두사가 있어야 하기 때문에 role 앞에 붙혀서 추가
        collection.add((GrantedAuthority) () -> ROLE_PREFIX + user.getRole().getRoleType());

        return collection;
    }

    public User getUser() {

        return user;
    }

    public String getRole() {

        return user.getRole().getRoleType();
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return user.getVisible();
    }
}
