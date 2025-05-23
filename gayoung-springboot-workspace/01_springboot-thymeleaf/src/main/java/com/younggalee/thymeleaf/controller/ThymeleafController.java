package com.younggalee.thymeleaf.controller;

import com.younggalee.thymeleaf.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {
    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/expression") // url 경로
    public String expressionTest(Model model, HttpSession session){
        // model : 리턴되는 페이지에서만 쓸 수 있음
        // 더 넓은 범위 session : 브라우저 동작되는 동안 사용할 수 있음
        model.addAttribute("name", "younggalee");
        model.addAttribute("age", "<em>20</em>");
        model.addAttribute("user", new UserDto(1, "홍길동", 20, LocalDate.now()));
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("forecast", Map.of("weather", "sunny", "temperature", 20.0));

        session.setAttribute("name", "younggalee2");
        return "expression"; // 응답할 페이지 (url 경로와 동알하다면 생략가능(void))
    }

    @GetMapping("/javascript")
    public void javascriptTest(Model model){
        model.addAttribute("message", "안녕하세요");
        if(Math.random() < 0.5){
            model.addAttribute("result", "Good");
        }
    }  // url 경로와 응답할 페이지가 같아서 생략된 모습

    @GetMapping("/utility")
    public void utilityTest(Model model){
        model.addAttribute("title", "spring boot project");
        model.addAttribute("content", "");
        model.addAttribute("stock", 3000.5);
        model.addAttribute("seasons", Arrays.asList("봄", "여름", "가을", "겨울"));
        model.addAttribute("now", LocalDate.now());
    }

    @GetMapping("/control")
    public void controlTest(Model model){
        System.out.println(model);  // {}
        model.addAttribute("score", 70);
        model.addAttribute("grade", "C");

        List<UserDto> userList = new ArrayList<>();
        if(Math.random() < 0.5) {
            userList.add(new UserDto(1, "이가영", 20, LocalDate.now()));
            userList.add(new UserDto(2, "홍길동", 20, LocalDate.now()));
            userList.add(new UserDto(3, "강개똥", 20, LocalDate.now()));
        }

        model.addAttribute("users", userList);
        System.out.println(model);
        /* {score=70, grade=C,
        users=[UserDto(no=1, name=이가영, age=20, registDate=2025-05-23),
               UserDto(no=2, name=홍길동, age=20, registDate=2025-05-23),
               UserDto(no=3, name=강개똥, age=20, registDate=2025-05-23)]}
         */
    }
}

