package com.sotogito.app.controller;

/*
    회원가입관련
    - 회원가입페이지요청     : GET  /user/signup.page
    - 아이디중복체크(비동기) : GET  /user/idcheck.do - param(checkId=입력값)
    - 회원가입요청           : POST /user/signup.do  - param(폼입력값들)

    로그인/로그아웃관련
    - 로그인요청         : POST /user/signin.do  - param(userId=입력값,userPwd=입력값)
    - 로그아웃요청       : GET  /user/signout.do

    마이페이지관련
    - 마이페이지요청     : GET  /user/myinfo.page
    - 프로필변경요청     : POST /user/modifyProfile.do - param(uploadFile=첨부파일)
    - 정보수정요청       : POST /user/modify.do - param(폼입력값들)
    - 회원탈퇴요청       : POST /user/resign.do - param(userPwd=검증을위한비번)
 */

import com.sotogito.app.dto.UserDto;
import com.sotogito.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/signup.page")
    public void signupPage() {
    } // 회원가입페이지 이동용

    @GetMapping("/myinfo.page")
    public void myinfoPage() {
        /// 로그인 여부 체크 필요
    } // 마이페이지 이동용

    @ResponseBody
    @GetMapping("/idcheck.do")
    public String idcheck(String checkId) {
        return userService.getUserCount(checkId) == 0 ? "NOTUSED" : "USED";
    }

    @PostMapping("/signup.do")
    public String signup(@ModelAttribute UserDto user,
                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", userService.registUser(user).get("message"));
        return "redirect:/";
    }

    @PostMapping("/signin.do")
    public String signin(@ModelAttribute UserDto user,
                         RedirectAttributes redirectAttributes,
                         HttpSession session,
                         HttpServletRequest request) {

        Map<String, Object> map = userService.loginUser(user);
        redirectAttributes.addFlashAttribute("message", map.get("message"));

        if (map.get("user") != null) {
            session.setAttribute("loginUser", map.get("user"));
        }

        return "redirect:" + request.getHeader("referer"); ///로그인시 최근 페이지로 redirect
        /// 검사 -> 네트워크 -> 헤더에서 Referer 확인 가능
    }

    @GetMapping("/signout.do")
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping(value = "/modifyProfile.do", produces = "text/html; charset=UTF-8")
    public String modifyProfile(MultipartFile uploadFile, HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Map<String, Object> map = userService.modifyUserProfile(loginUser.getUserId(), uploadFile);
        if (map.get("profileURL") != null) { // 프로필변경성공
            loginUser.setProfileURL((String) map.get("profileURL"));
        }
        return (String) map.get("message");
    }

}
