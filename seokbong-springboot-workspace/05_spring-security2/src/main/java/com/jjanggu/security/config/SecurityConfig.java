package com.jjanggu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // 비밀번호 암호화 관련 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(auth->{
            auth.requestMatchers("/", "/login", "/signup","/images/**").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/user/**").hasAuthority("USER")
                    .anyRequest().authenticated();
        });

        http.formLogin(form -> {
            form.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("userId")
                    .passwordParameter("userPwd")
                    //.defaultSuccessUrl("/", true)
                    .successHandler(((request, response, authentication) -> {
                        // 권한별 각기 다른 url로 redirect
                        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
                            response.sendRedirect("/admin/main");
                        }else{
                            response.sendRedirect("/user/main");
                        }
                    }))
                    .failureUrl("/login?error");
        });

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
