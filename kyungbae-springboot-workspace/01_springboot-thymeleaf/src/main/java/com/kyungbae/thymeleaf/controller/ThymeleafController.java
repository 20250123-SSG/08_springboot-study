package com.kyungbae.thymeleaf.controller;

import com.kyungbae.thymeleaf.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {

    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/expression")
    public void expressionTest(Model model, HttpSession httpSession){

        model.addAttribute("name", "KB");
        model.addAttribute("age", "<strong>30</strong>");
        model.addAttribute("user", new UserDto(1, "최종일", 30, LocalDateTime.now()));
        model.addAttribute("forecast", Map.of("weather", "sunny", "temperature", 20.0));

        httpSession.setAttribute("userName", "kakaba");

    }

    @GetMapping("/javascript")
    public void javascriptTest(Model model){
        model.addAttribute("message", "안녕하세요.");
        if (Math.random() < 0.5) {
            model.addAttribute("result", "nice");
        }
    }

    @GetMapping("/utility")
    public void utilityTest(Model model){
        model.addAttribute("title", "spring boot project");
        model.addAttribute("content", "");
        model.addAttribute("stock", 3000.5);
        model.addAttribute("seasons", Arrays.asList("봄", "여름"));
        model.addAttribute("now", LocalDateTime.now());
    }

    @GetMapping("/control")
    public void controlTest(Model model){
        model.addAttribute("score", 99);
        model.addAttribute("grade", 'C');

        List<UserDto> userList = new ArrayList<>();
        if (Math.random() < 0.5) {
            userList.add(new UserDto(1, "최종일", 30, LocalDateTime.now()));
            userList.add(new UserDto(2, "최종이", 30, LocalDateTime.now()));
            userList.add(new UserDto(3, "최종삼", 30, LocalDateTime.now()));
        }

        model.addAttribute("userList", userList);
    }
}
