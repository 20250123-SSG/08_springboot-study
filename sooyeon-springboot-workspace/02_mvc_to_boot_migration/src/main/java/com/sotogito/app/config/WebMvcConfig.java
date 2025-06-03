package com.sotogito.app.config;

import com.sotogito.app.interception.SignInCheckInterception;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
        ## WebMvcConfigurer ##
        1) 인터셉터 등록
        2) 리소스 핸들링
        3) 뷰리졸버 세팅
        4) 메세지 변환
           등등
     */

    private final SignInCheckInterception signInCheckInterception;

    // 리소스 핸들링
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // servlet-context.xml - <mvc:resources mapping="/upload/**" location="file:///upload/"/>
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInCheckInterception) /// 로그인체크 인터셉터에 대한 url지정
                .addPathPatterns("/user/myinfo.page")
                .addPathPatterns("/board.regist.page");

    }

}
