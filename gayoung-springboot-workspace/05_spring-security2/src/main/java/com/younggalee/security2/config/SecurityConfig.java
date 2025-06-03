package com.younggalee.security2.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // 비밀번호 암호화 관련 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 이하 security 프로젝트에서 다룬 내용
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/signup", "/images/**").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/user/**").hasAuthority("USER")
                    .anyRequest().authenticated();
        });

        // 로그인 처리 설정 (인증 설정) => 사용자 정의 로그인폼 설정 (POST)
        http.formLogin(form -> {
            form.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("userId")
                    .passwordParameter("userPwd")
                    .failureUrl("/login?error")
                    //.defaultSuccessUrl("/", true);
                    .successHandler((request, response, authentication) -> {
                        // 권한별 각기 다른 url로 redirect
                        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) { //authentication.getAuthorities()는 simpleGrant~로된 list형태
                            response.sendRedirect("/admin/main");
                        } else {
                            response.sendRedirect("/user/main");
                        }
                    });
                    //failureURL도 마찬가지
        });

        //로그아웃 처리 설정 (인증설정) => 사용자 정의 (GET)
        http.logout(logout -> {
            logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
        });
        http.csrf(csrf -> csrf.disable());


        return http.build();
    }
}
