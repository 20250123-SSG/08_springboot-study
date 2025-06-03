package com.ino.app.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class SigninCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // boolean return : true 반환시 - 기 요청 흐름대로 Controller 실행
        //                  false "     - Controller 실행x
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null){ // login x

            // RedirectAttributes의 flashAttribute로 메세지 담기
            FlashMap flashMap = new FlashMap();
            flashMap.put("message", "can use after login");
            RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flashMap, request, response);

            response.sendRedirect("/");
            return false;
        }else{ // login o
            return true;
        }
    }
}
