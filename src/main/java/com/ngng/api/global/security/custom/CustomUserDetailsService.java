package com.ngng.api.global.security.custom;

import com.ngng.api.user.user.entity.User;
import com.ngng.api.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //DB에서  해당 유저가 존재하는지 조회
        User userData = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid User"));

        return new CustomUserDetails(userData);
    }
}
