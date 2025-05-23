package com.jjanggu.thymeleaf.controller;

import com.jjanggu.thymeleaf.dto.UserDto;
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
        //  /resources/templates/ + xxxxx + .html
    }

    @GetMapping("/expression")
    public void expressionTest(Model model, HttpSession session){

        model.addAttribute("name", "kangbroo");
        model.addAttribute("age", "<em>20</em>");
        model.addAttribute("user", new UserDto(1, "홍길동", 20, LocalDateTime.now()));
        model.addAttribute("forecast", Map.of("weather", "sunny", "temperature", 20.0));

        session.setAttribute("userName", "boram");

        //return "expression";
    }

    @GetMapping("/javascript")
    public void javascriptTest(Model model){
        model.addAttribute("message", "안녕하세요");
        if(Math.random() < 0.5){
            model.addAttribute("result", "굿");
        }
    }

    @GetMapping("/utility")
    public void utilityTest(Model model){
        model.addAttribute("title","spring boot project");
        model.addAttribute("content", "");
        model.addAttribute("stock", 3000.5);
        model.addAttribute("seasons", Arrays.asList("봄", "여름", "가을", "겨울"));
        model.addAttribute("now", LocalDateTime.now());
    }

    @GetMapping("/control")
    public void controlTest(Model model){
        model.addAttribute("score", 75);
        model.addAttribute("grade", "C");

        List<UserDto> userList = new ArrayList<>();
        if(Math.random() < 0.5){
            userList.add(new UserDto(1, "홍길동", 20, LocalDateTime.now()));
            userList.add(new UserDto(2, "김말순", 30, LocalDateTime.now()));
            userList.add(new UserDto(3, "강개똥", 40, LocalDateTime.now()));
        }

        model.addAttribute("userList", userList);
    }

}
