package com.younggalee.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@EnableWebSecurity
@Controller
public class SecurityConfig {

    @Bean
    // 재정의하는 순간 기존설정적용안되기 때문에 기존거 사용하고 싶으면 다시 써줘야함.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // URL 접근 제어 (인가 설정)
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/signup", "/images/**").permitAll() // 별도의 인증처리 없이 허용하겠다.
                    .anyRequest().authenticated(); // 그밖에 모든 페이지는 접근제어
        });

        //로그인 처리 설정 (인증 설정) => 기존 메커니즘 설정 (기존 로그인 폼 방식)
        //http.formLogin(Customizer.withDefaults());

        // 직접 만든 로그인 페이지로 로그인처리 해보기 (커스텀 form)
        // 로그인 처리 설정 (인증 설정) => 사용자 정의 로그인폼 설정 (POST)
        http.formLogin(form -> {
            form.loginPage("/login")
                    .loginProcessingUrl("/login") //실제 로그인요청을 보내는 url : 이때 로그인 처리하겠습니다.
                    .usernameParameter("userId") // 이때 넘어오는 값중 인증처리에 필요한 유저내임
                    .passwordParameter("password") // 이 값들인증처리에 사용하겠다
                    .failureUrl("/login?error") // 인증 실패했을 때, 리다이렉트할 경로
                    .defaultSuccessUrl("/", true); // 인증 성공했을 때 리다이렉트할 url (세션아이디 발급되어 쿠기에 저장됨)
                });

        //로그아웃 처리 설정 (인증설정) => 사용자 정의 (GET)
        http.logout(logout -> {
            logout.logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout") // 성공시 보여줄 페이지 리다이렉트
                    .invalidateHttpSession(true) // 세션에 저장되어있던거 지우기 (세션 무효화)
                    .deleteCookies("JSESSIONID"); // 쿠키에 있던거 지우기
        });
        http.csrf(csrf -> csrf.disable());


        return http.build(); //SecurityFilterChain 객체로 반환
    }
}
