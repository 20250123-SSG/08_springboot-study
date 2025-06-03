package com.ino.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // URL 접근 제어( 인가 설정 )
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/signup", "/images/**").permitAll()
                    .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .requestMatchers("/user/**").hasAnyAuthority("USER")
                    .anyRequest().authenticated();
        });

        // 로그인 처리 설정 ( 인증 설정)

//        http.formLogin(Customizer.withDefaults());


        // 로그인 처리 설정( 인증 설정 ) => custom
        http.formLogin(form -> {
            form.loginPage("/login") // custom loginPage URL
                    .loginProcessingUrl("/login") // login Process URL(POST)
                    .usernameParameter("userId") // 파라미터중 인증처리 사용 아이디
                    .passwordParameter("userPwd") // 파라미터중 인증처리 사용 비밀번호
//                    .defaultSuccessUrl("/", true) // redirect URL, 성공시 새 JSESSIONID가 cookie에 저장
                    .successHandler((req, resp, auth) -> {
                        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                            resp.sendRedirect("/admin/main");
                        } else {
                            resp.sendRedirect("/user/main");
                        }
                    })
//                    .failureUrl("/login?error"); // 실패시 Redirect URL 지정
                    .failureHandler((req, resp, exce) -> {
                        log.info("exception : {}", exce.getMessage());
                        resp.sendRedirect("/login?error");
                    });
        });

        // 로그아웃 처리 설정( 인증 설정 )
        http.logout(logout -> {
            logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
        });


        http.csrf(csrf -> {
            csrf.disable();
        });

        return http.build();

    }
}
