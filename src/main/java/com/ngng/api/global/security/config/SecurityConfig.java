package com.ngng.api.global.security.config;

import com.ngng.api.global.security.handler.CustomLogoutHandler;
import com.ngng.api.global.security.handler.CustomLogoutSuccessHandler;
import com.ngng.api.global.security.jwt.filter.LoginFilter;
import com.ngng.api.global.security.jwt.filter.TokenFilter;
import com.ngng.api.global.security.jwt.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final TokenUtils tokenUtils;
    private final CustomLogoutHandler logoutHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 해당하는 설정들을 사용하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // localhost:3000 cors 해제, 테스트용도
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler)
                )

                // token을 통해 인증을 처리하기 때문에 session을 stateless 처리
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                /*
                * filter를 bean으로 주입 시 spring filter에 먼저 적용된 후
                * security filter에도 적용되어 2번 동작하기 때문에 bean주입을 하지않고 new를 통해 생성
                * jwt를 통한 인증/인가 필터 설정
                * */
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), tokenUtils), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenFilter(tokenUtils), LoginFilter.class)

                /*
                * 요청별 권한 설정
                * Role 종류
                * ADMIN : 관리자
                * USER : 유저
                * UNCONFIRMED_USER : 계좌 인증을 하지 않은 유저
                * */
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/search/**").permitAll() // 메인페이지, 검색은 누구나 가능
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll() // 상품 조회는 누구나 가능

                        .requestMatchers("/login", "/join", "/find/**").anonymous() // 회원가입, 로그인, 아이디/비밀번호 찾기 등은 비로그인 유저만 가능

                        // 채팅, 상품 등록, 수정, 삭제는 계좌 인증을 완료한 유저 혹은 관리자만 가능
                        .requestMatchers("/chat/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/product").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAnyRole("USER", "ADMIN")

//                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자페이지
                        .requestMatchers("/admin/**").permitAll()

                        .requestMatchers("/user/**", "/confirm", "/logout").authenticated() // 마이페이지 등은 로그인한 유저 모두가 사용 가능 + 로그인도

                        .anyRequest().permitAll() // 존재하지 않은 요청은 404 NotFound
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
