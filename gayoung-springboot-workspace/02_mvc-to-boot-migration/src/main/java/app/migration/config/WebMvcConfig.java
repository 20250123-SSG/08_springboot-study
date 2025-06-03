package app.migration.config;

import app.migration.interceptor.SigninCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
//환경설정용 클래스 (자바방식)
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
        ## WebMvsConfigurer ##
        1) 인터셉터 등록
        2) 리소스 핸들링
        3) 뷰리졸버 세팅
        4) 메세지 변환
        등등
     */

    private final SigninCheckInterceptor signinCheckInterceptor;

    //리소스 핸들링
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // servlet-context.xml - <mvc:resources mapping="/upload//**" location="file:///upload/"/>
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///upload/");
    }

    // 인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signinCheckInterceptor)
                .addPathPatterns("/user/myinfo.page") //어떤 url 요청시 구동시킬 것인가
                .addPathPatterns("/board/regist.page");
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
