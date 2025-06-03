package app.migration.controller;

/*
    회원서비스
    - 회원가입페이지요청 : GET  /user/signup.page
    - 아이디중복체크(비동기)    : GET  /user/idcheck.do - param(checkId=입력값)
    - 회원가입요청     : POST  /user/signup.do - param(폼입력값들)

    로그인/로그아웃관련
    - 로그인요청      : POST  /user/signin.do - param(userId=입력값, userPwd=입력값)
    - 로그아웃요청     : GET   /user/signout.do

    마이페이지관련
    - 마이페이지요청    : GET  /user/myinfo.page
    - 프로필변경요청    : POST  /user/modifyProfile.do - param(uploadFile=첨부파일)
    - 정보수정요청     : POST  /user/modify.do - param(폼입력값들)
    - 회원탈퇴요청     : POST  /user/resign.do - param(userPwd=검증을 위한 비번)
 */

import app.migration.dto.UserDto;
import app.migration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public void signupPage(){} // 회원가입페이지 이동용

    @GetMapping("/myinfo.page")
    public void myinfoPage(){
        //로그인 여부체크 필요 (servlet>controller 넘어가기전 인터셉터에서)
    } // 마이페이지 이동용

    @ResponseBody
    @GetMapping("/idcheck.do") //해당 url로
    public String idcheck(String checkId){
        return userService.getUserCount(checkId) == 0 ? "not used" : "used";  // 해당 데이터를 @responsebody에 담아 전달한다.
    }

    @PostMapping("/signup.do")
    public String signup(UserDto user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", userService.registUser(user).get("message"));
        return "redirect:/";
        /* Network 탭을 황인
           - /signup.do      응답코드 302 (요청후, 리다이렉트)
           - localhost       응답코드 200 (일반적인 포워딩)
         */
    }

    @PostMapping("/signin.do")
    public String signin(UserDto user, RedirectAttributes redirectAttributes, HttpSession session
                            , HttpServletRequest request) {
        Map<String, Object> map = userService.loginUser(user);
        // 로그인 성공시에만 dto 존재함. >> session에 담기
        redirectAttributes.addFlashAttribute("message", map.get("message"));

        if(map.get("user") != null) { //로그인 성공일 경우
            session.setAttribute("loginUser", map.get("user"));
        }

        //return "redirect:/"; // 응답코드 302 / 로그인만 하면 매번 main으로 가는 이유
        return "redirect:" + request.getHeader("referer"); //signin.do 이전에 보고 있던 페이지 URL 주소 가져오기 (재요청)
    }

    @GetMapping
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping(value = "/modifyProfile.do", produces="text/html; charset=UTF-8")
    public String modifyProfile(MultipartFile uploadFile, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("loginUser");
        Map<String, Object> map = userService.modifyUserProfile(user.getUserId(), uploadFile);
        if(map.get("profileURL") != null) { // 프로필변경성공 (UserServiceImpl - modify~ 성공시 return값 참고)
            user.setProfileURL(map.get("profileURL").toString()); //로그인시 가지고 온 session객체에 업데이트
        }
        return (String) map.get("message");
    }

}














