package com.kyungbae.app.config;

import com.kyungbae.app.interceptor.SigninCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
        ## WebMvcConfigurer
        1) 인터셉터 등록
        2) 리소스 핸들링
        3) 뷰리졸버 세팅
        4) 메세지 변환
        등
     */

    private final SigninCheckInterceptor signinCheckInterceptor;

    // 리소스 핸들링
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //   <mvc:resources mapping="/upload/**" location="file:///upload/"/>
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///upload/");
    }

    /**
     * interceptor 등록
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signinCheckInterceptor)
                .addPathPatterns("/user/myinfo.page")
                .addPathPatterns("/board/regist.page");
    }
}
