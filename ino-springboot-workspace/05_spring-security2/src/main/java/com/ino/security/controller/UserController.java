package com.ino.security.controller;


import com.ino.security.auth.LoginUser;
import com.ino.security.dto.UserDto;
import com.ino.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public void loginPage(){}

    @GetMapping("/signup")
    public void singupPage(){}

    @GetMapping("/admin/main")
    public void adminMainPage(){}

    @GetMapping("/user/main")
    public void userMainPage(@AuthenticationPrincipal LoginUser loginUser){
        System.out.println("id: " + loginUser.getUsername());
        System.out.println("pw: " + loginUser.getPassword());
        System.out.println("auth: " + loginUser.getAuthorities());
        System.out.println("userName : " + loginUser.getDisplayName());
    }

    @PostMapping("/signup")
    public String signup(UserDto user, RedirectAttributes ra){
        Map<String, String> map = userService.registUser(user);
        ra.addFlashAttribute("message", map.get("message"));
        return "redirect:" + map.get("path");
    }
}
