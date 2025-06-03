package com.jjanggu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")       // 인증 처리 필요 없음
    public void loginPage(){}

    @GetMapping("/signup")      // 인증 처리 필요 없음
    public void signupPage(){}

    @GetMapping("/admin/main")
    public void adminMainPage(){}

    @GetMapping("/user/main")
    public void userMainPage(){}

}
