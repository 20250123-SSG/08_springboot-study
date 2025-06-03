package com.ino.app.controller;

import com.ino.app.dto.UserDto;
import com.ino.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
/*
    회원 서비스
    - 회원가입페이지 요청 : GET /user/signup.page
    - 아이디 중복 체크 : GET /user/idcheck.do - param(checkId=입력값)
    - 회원가입요청  : POST /user/signup.do - param(form 입력값)

    로그인/로그아웃 관련
    - 로그인 요청 : POST /user/signin.do - param(userId=입력값,userPwd=입력값)
    - 로그아웃 요청 : GET /user/signout.do

    마이페이지 관련
    - 마이페이지 요청 : GET /user/myinfo.page
    - 프로필 변경 요청 : POST /user/modifyProfile.do - param(uploadFile=첨부파일)
    - 정보 수정 요청 : POST /user/modify.do - param(폼 입력값)
    - 회원 탈퇴 요청 : POST /user/resign.do - param(userPwd=검증용비번)

 */

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup.page")
    public void signupPage() {
    }

    @GetMapping("/myinfo.page")
    public void myinfoPage() {
    }

    @ResponseBody
    @GetMapping("/idcheck.do")
    public String idcheck(String checkId) {
        return userService.getUserCount(checkId) == 0 ? "NOTUSED" : "USED";
    }


    @PostMapping("/signup.do")
    public String signup(UserDto user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", userService.registUser(user).get("message"));
        return "redirect:/";
        /*
            Network 탭 확인
            - /signup.do 응답코드 : 302
            - localhost 응답코드 : 200
         */
    }

    @PostMapping("/signin.do") // login
    public String signin(UserDto user
            , RedirectAttributes redirectAttributes
            , HttpSession session
            , HttpServletRequest request) {
        Map<String, Object> map = userService.loginUser(user);
        redirectAttributes.addFlashAttribute("message", map.get("message"));
        if (map.get("user") != null) {
            session.setAttribute("loginUser", map.get("user"));
        }

        return "redirect:" + request.getHeader("referer"); // 이전에 보던 페이지 url 재요청
    }

    @GetMapping("/signout.do")
    public String signout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping(path = "/modifyProfile.do", produces="text/html; charset=UTF-8")
    public String modifyProfile(MultipartFile uploadFile, HttpSession session){
        UserDto loginUser = (UserDto)session.getAttribute("loginUser");
        Map<String, Object> map = userService.modifyUserProfile(loginUser.getUserId() , uploadFile);
        if(map.get("profileURL") != null) {
            loginUser.setProfileURL((String) map.get("profileURL"));
            session.setAttribute("loginUser", loginUser);
        }
        return (String)map.get("message");
    }
}
