package com.ino.rest.section04.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
    Swagger
    API 문서 자동 생성 라이브러리
    초기 Swagger는 API 설계, 빌드, 문서화에만 사용
    현재는 API 문서화, 테스트 기능 등 제공함
    need SpringDoc OpenAPI Starter Dependency (prev ver: Spring fox)
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Spring Boot REST API")
                        .description("REST API based user Service")
                        .version("1.0.0"));
    }
}
