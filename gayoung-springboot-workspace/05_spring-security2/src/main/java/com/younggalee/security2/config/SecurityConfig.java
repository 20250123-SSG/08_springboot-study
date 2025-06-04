package com.younggalee.security2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Spring Security는 DispatcherServlet보다 먼저 동작하는 필터 체인이다.

@Slf4j
@EnableWebSecurity //보안설정활성화하는 어노테이션 (저 설정 따로 씁니다~ 스프링한테 알림)
// 커스텀보안설정하는 스프링 서큐리티 필터체인을 스프링에 등록하고 활성화시켜주는 어노테이션
@Configuration
public class SecurityConfig {

    // 비밀번호 암호화 관련 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 이하 security 프로젝트에서 다룬 내용
    // securityFilterChain은 보안설정을 정의하는 빈이다. : (인증 및 인가) 어떤 경로에 인증이 필요한지, 허용할지, 로그인.아웃.세션 처리를 어떻게 할지
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity는 SecurityFilterChain 보안 필터 체인을 만들기 위한 빌더 클래스 (builder 객체란?  객체만들기를 쉽게하도록 길라잡이해주는 객체)
        // 내부의 여러 메소드들을 통해 여러가지 보안을 설정함.


        // authorizeHttpRequests: 경로별 접근 권한 설정
        // requestMatchers: 특정 url패턴에 대한 조건 설정 // ex) "/admin/**" : /admin/으로 시작하는 모든 경로
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/signup", "/images/**").permitAll() // 누구나 접근 가능(인증필요없음/로그인도 X)
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") //해당 권한을 가진 사람들만
                    .requestMatchers("/user/**").hasAuthority("USER")
                    .anyRequest().authenticated(); // 인증된 사용자만 접근 가능 (로그인)
        });

        // 로그인 처리 설정 (인증 설정) => 사용자 정의 로그인폼 설정 (POST)
        http.formLogin(form -> {
            form.loginPage("/login") //로그인페이지로 사용할 url 지정
                    .loginProcessingUrl("/login") // 로그인 폼이 post방식으로 제출될 url // 컨트롤러에서 만들지 않아도 spring security가 자동 처리
                    .usernameParameter("userId") //로그읜 폼의 name이 username, password가 아닐경우 명시해주기
                    .passwordParameter("userPwd")
                    .failureUrl("/login?error") // 리다이렉트
                    // 물음표: 쿼리 스트링 // error : 쿼리 파라미터
                    //.defaultSuccessUrl("/", true); // true 의미: 성공하면 무조건 "/"여기로 가라
                    // false일 경우: 로그인 성공시 로그인하기전 원래가려던 페이지 Url로 돌아간다(리다이렉트). 단 직접 /login 페이지로 와 로그인을 한 경우에는 "/"로 이동

                    // 인증 성공했다면, 여기서부터는 인가
                    // 권한별로 다른 페이지로 보내야할때 핸들러 사용
                    // 3가지 객체 주입됨. spring security가 자동으로 넘겨줌
                    .successHandler((request, response, authentication) -> {
                                       // 로그인 폼 요청             // 로그인 성공후, 응답을 보낼 객체     // 로그인 성공한 사용자 정보

                        // authentication 인증결과 객체 //Security context에 저장된다.
                        // getName() : id(username)
                        //getCredentials() : 비밀번호
                        //getAuthorities() // 권한 목록 (ex ADMIN, USER)
                        //getPrincipal() //User, LoginUser, UserDetails 구현체
                        //isAuthenticated() : 인증여부


                        // 권한별 각기 다른 url로 redirect
                        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) { //Authentication.getAuthorities()는 Collection<? extends GrantedAuthority> 타입입니다.
                           //Authentication.getAuthorities()는 Collection<? extends GrantedAuthority> 타입입니다.
                            // Spring Security는 “복수 권한 보유”를 기본 전제로 설계되어 있기 때문이에요.
                            //즉, 한 사용자가 **여러 개의 권한(역할)**을 가질 수 있다는 걸 허용합니다.

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
