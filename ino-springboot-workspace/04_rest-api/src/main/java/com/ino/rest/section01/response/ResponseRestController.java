package com.ino.rest.section01.response;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Hidden
@RequestMapping("/response")
@RestController
public class ResponseRestController {

    //1. 문자열 자원 응답
    @GetMapping("/text")
    public String getText() {
        return "HelloWorld";
    }

    // 2. 숫자 자원 응답
    @GetMapping("/number")
    public int getNumber() {
        return (int) (Math.random() * 10 + 1);
    }

    // 3. Object 자원 응답
    @GetMapping("/message")
    public Message getMessage() {
        return new Message(200, "success");
        // JSON으로 자동변환되어 반환하는걸 볼 수 있다.
    }

    // 4. List 자원 응답
    @GetMapping("/list")
    public List<String> getList() {
        return List.of("spring", "summer", "autumn", "winter");
    }

    // 5. Map resource response
    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        return Map.of(200, "OK", 400, "BAD_REQUEST", 500, "INTERNAL_ERROR");
    }

    // 6. image response(image -> binary data)
    // 그냥 요청할 경우 이상하게 보임 -> produces 설정을 통해 multipartform으로 변경해줘야함(default = application/json)
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/editconfiguration.png").readAllBytes();
    }
}
