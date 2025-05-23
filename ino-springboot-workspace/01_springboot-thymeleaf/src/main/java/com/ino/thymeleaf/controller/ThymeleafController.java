package com.ino.thymeleaf.controller;

import com.ino.thymeleaf.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {
    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/expression")
    public String expressionTest(Model model, HttpSession session){

        model.addAllAttributes(Map.of("name","ino", "age", "<em>20</em>", "user", UserDto.builder().age(20).name("ino").no(1).registDate(LocalDateTime.now()).build(), "forecast", Map.of("weather", "sunny", "temp", 20.0)));
        session.setAttribute("message" , "hello");
        return "expression";
    }

    @GetMapping("/javascript")
    public void javascriptTest(Model model){
        model.addAttribute("message", "helloworld");
        if(Math.random() < 0.5){
            model.addAttribute("result", "good");
        }
    }

    @GetMapping("/utility")
    public void utilityTest(Model model){
        model.addAttribute("title", "spring boot project");
        model.addAttribute("content" , "");
        model.addAttribute("stock", 3000.5);
        model.addAttribute("season", List.of("spring", "summer", "fall", "winter"));
        model.addAttribute("now", LocalDateTime.now());
    }

    @GetMapping("/control")
    public void controlTest(Model model) {
        int num = (int)(Math.random() * 100) + 1;
        model.addAttribute("score", num);
        model.addAttribute("grade", "f");
        List<UserDto> userList = new ArrayList<>();
        if (Math.random() < 0.5) {
            userList.add(new UserDto(1, "ino", 20, LocalDateTime.now()));
            userList.add(new UserDto(2, "ino2", 25, LocalDateTime.now()));
            userList.add(new UserDto(3, "ino3", 30, LocalDateTime.now()));
        }

        model.addAttribute("list", userList);
    }
}
