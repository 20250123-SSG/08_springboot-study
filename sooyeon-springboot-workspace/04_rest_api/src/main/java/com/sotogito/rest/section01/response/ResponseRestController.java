package com.sotogito.rest.section01.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ## @RestController
 * 1. @Controller + @Responsebody를 합친 어노테이션
 * 2. 클래스 레벨에 작성
 * 3. 해당 클래스 내의 모든 핸들러메서드에 @Responsebody이 적용
 */
@RequestMapping("/response")
//@Controller
@RestController
public class ResponseRestController {

    /// 1. 문자열 자원 응답
    @GetMapping("/text")
    public String getText() {
        return "hello";
    }

    @GetMapping("/number")
    public int getNumber() {
        return (int) (Math.random() * 10 + 1);
    }

    @GetMapping("/message")
    public Message getMessage() { //{status: 200, message: "메시지내용"}
        return new Message(HttpStatus.OK.value(), "메시지내용");
    }

    @GetMapping("/list")
    public List<String> getList() { //["봄", "여름", "가을", "겨울"]
        return List.of("봄", "여름", "가을", "겨울");
    }

    @GetMapping("/map")
    public Map<Integer, String> getMap() { //{200: "정상 응답", 400: "잘못된 요청", 500: "개발자 실수"}
        return Map.of(
                200, "정상 응답",
                400, "잘못된 요청",
                500, "개발자 실수"
        );
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/forest1.jpg").readAllBytes();
    }


}
