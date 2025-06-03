package com.ino.app.config;

import com.ino.app.interceptor.SigninCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final SigninCheckInterceptor signinCheckInterceptor;
    /*
        WebMvcConfigurer
        인터셉터 등록
        리소스 핸들링
        뷰 리졸버 세팅
        메세지 변환
        등등
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // servlet-context.xml - <mvc:resource mapping="/upload/**" location="file:///upload/"/>
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signinCheckInterceptor)
                .addPathPatterns("/user/myinfo.page")
                .addPathPatterns("/board/regist.page");
    }
}
