package com.kyungbae.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")       // 인증X
    public void loginPage(){}

    @GetMapping("/signup")      // 인증X
    public void siginupPage(){}

    @GetMapping("/admin/main")
    public void adminMainPage(){}

    @GetMapping("/user/main")
    public void userMainPage(){}

}
