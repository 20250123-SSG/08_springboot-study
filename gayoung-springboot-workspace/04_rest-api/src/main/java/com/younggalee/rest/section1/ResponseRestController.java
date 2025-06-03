package com.younggalee.rest.section1;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/*
    ## @RestController ##
    1. @Controller와 @ResponseBody 가 합쳐진 어노테이션
    2. 클래스 레벨에 작성, 해당 클래스 내의 모든 핸들러 메소드에 @ResponseBody가 묵시적으로 적용됨
 */


@RequestMapping("/response")
@RestController // 로 등록하면 안의 모든 클래스에 requestBody
public class ResponseRestController {

    // 1. 문자열 자원 응답
    @GetMapping("/text")
    public String getResponse() {
        return "Hello World";
    }

    // 2. 숫자 자원 응답
    @GetMapping("/number")
    public int getNumber() {
        return (int)(Math.random() * 10 + 1);
    }

    // 3. Obect 자원 응답
    @GetMapping("/message")
    public Message getMessage() {
        return new Message(200, "메세지내용");
    }

    // 4. List 자원 응답
    @GetMapping("/list")
    public List<String> getList() {
        return List.of("봄","여름","가을", "겨울");
    }

    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        return Map.of(200, "정상응답"
                    , 400, "잘못된 요청"
                    , 500, "개발자의 실수");
    }

    // 6. Image 자원 응답
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/test_img.png").readAllBytes();
    }
}