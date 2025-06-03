package com.younggalee.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 페이지 이동
    @GetMapping("/login")   //인증필요없음
    public void login(){}
    @GetMapping("/signup")   //인증필요없음
    public void signup(){}
    @GetMapping("/admin/main")
    public void main(){}
    @GetMapping("/user/main")
    public void userMainPage(){}
}
