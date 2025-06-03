package com.podoseee.app.interceptor;

/*
    ## 인터셉터(Interceptor) ##
    1. 특정 Controller측 핸들러 메소드가 실행되기 전, 실행된 후, 뷰렌더링이 완료된 후 시점에 동작시킬 내용을 정의
    2. HandlerInterceptor를 구현하는 클래스로 정의한 후 시점에 해당하는 메소드를 오버라이딩 해서 재정의 하면됨
        1) preHandle    : DispatcherServlet이 요청에 맞는 핸들러메소드(Controller)를 호출하기 전에 동작
        2) postHandle   : 핸들러메소드(Controller)에서 요청처리 후 DispatcherServlet으로 결과가 반환될 때 동작
        3) afterComplete: 뷰 렌더링이 완료된 이후(클라이언트에 응답이 전송된 후)에 동작
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class SigninCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // boolean 반환 : true 반환시  - 기존 정상흐름대로 Controller 실행
        //                false 반환시 - Controller 실행 x

        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") == null) { // 로그인이 되어있지 않을 경우

            // RedirectAttributes의 flashAttribute로 메세지 담기
            FlashMap flashMap = new FlashMap();
            flashMap.put("message", "로그인 후 이용가능한 서비스입니다.");
            RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flashMap, request, response);

            response.sendRedirect("/");

            return false;
        }else{ // 로그인이 되어있을 경우

            return true;
        }
    }
}
















