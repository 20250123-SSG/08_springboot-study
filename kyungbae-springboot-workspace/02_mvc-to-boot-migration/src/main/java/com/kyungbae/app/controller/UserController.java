package com.kyungbae.app.controller;

/*
    회원서비스
    - 회원가입페이지요청 : GET  /user/signup.page
    - 아이디중복채크     : GET  /user/idcheck.do - param(checkId=입력값)
    - 회원가입요청       : POST /user/signup.do  - param(form값들)

    로그인/아웃관련
    - 로그인요청         : POST /user/signin.do  - param(userId=입력값,userPwd=입력값)
    - 로그아웃요청       : GET  /user/signout.do

    마이페이지관련
    - 마이페이지요청     : GET  /user/myinfo.page
    - 프로필변경요청     : POST /user/modifyProfile.do - param(uploadFile=첨부파일)
    - 정보수정요청       : POST /user/modify.do  - param(form값들)
    - 회원탈퇴요청       : POST /user/resign.do  - param(userPwd=삭제검증비번요구)
 */

import com.kyungbae.app.dto.UserDto;
import com.kyungbae.app.service.UserService;
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

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/signup.page")
    public void signupPage(){} // 회원가입페이지 이동

    @GetMapping("/myinfo.page")
    public void myinfoPage(){} // 마이페이지 이동

    @ResponseBody
    @GetMapping("/idcheck.do")
    public String idcheck(String checkId){
        return userService.getUserCount(checkId) == 0 ? "NOTUSED" : "USED";
    }

    @PostMapping("/signup.do")
    public String signup(UserDto user, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", userService.registUser(user).get("message"));
        return "redirect:/"; // redirect라서 302 응답코드
        /*
            network 탭 확인
            - /signup.do 응답코드 302
            - localhost  응답코드 200
         */
    }

    @PostMapping("/signin.do")
    public String signin(UserDto user,
                         RedirectAttributes redirectAttributes,
                         HttpSession session,
                         HttpServletRequest request){ /*이전에 보던 page URL 찾기*/

        Map<String, Object> map = userService.loginUser(user);
        redirectAttributes.addFlashAttribute("message", map.get("message"));

        if (map.get("user") != null) { // 로그인 성공일 경우
            session.setAttribute("loginUser", map.get("user"));
        }
//        System.out.println(request.getHeader("referer")); // 이전에 보던 page url
        return "redirect:" + request.getHeader("referer"); // 응답코드 302
    }

    @GetMapping("/signout.do")
    public String signout(HttpSession session){
        session.invalidate(); // 로그아웃시 HttpSession 만료시키기
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping(value ="/modifyProfile.do", produces = "text/html; charset=UTF-8") // 반환하는것이 String 메세지고, 한글이 포함되어 있어서 charset 변경작업
    public String modifyProfile(MultipartFile uploadFile, HttpSession session){
        UserDto loginUser = (UserDto)session.getAttribute("loginUser");
        Map<String, Object> map = userService.modifyUserProfile(loginUser.getUserId(), uploadFile);
        if (map.get("profileURL") != null) { // 프로필 변경 성공
            loginUser.setProfileURL((String)map.get("profileURL"));
        }
        return (String)map.get("message");
    }
}
