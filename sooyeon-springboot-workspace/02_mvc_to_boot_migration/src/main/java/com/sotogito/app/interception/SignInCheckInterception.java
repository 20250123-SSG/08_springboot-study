package com.sotogito.app.interception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

/***
 * ## 인터셉터
 * 1. 특정 controller측 핸들러 메서드가 실행되기 전, 실핸됭 후, 뷰렌딩이 완료된 후 시점에서 동작시킬 내용을 정의
 * 2. HandlerInterception을 구현하는 클래스를 정의한 후 시점에 해당하는 메서드를 오버라이딩 해서 재엉의 하면됨
 *      1) preHandle  : DispatcherServlet이 요청에 마즌ㄴ 핸들러메서드(Controller)를 호출하기 전에 동작
 *      2) postHandle : 핸들러메서드(Controller)에서 요청처리 후 DispatcherServlet으로 결과가 반환될 때 동작
 *      3) afterComplete : 뷰 렌더링이 완료된 이후(클라이언트에 응답이 전송된 후)에 동작
 */
@Component
public class SignInCheckInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /**
         * boolean 반환 : true 반환시 - 기존 정상흐름대로 Controller 실행
         *              : false 반환시 - Controller 실행 X
         */
        HttpSession session = request.getSession();

        if(session.getAttribute("loginUser") == null) {  //로그인 X
            FlashMap flashMap = new FlashMap(); /// FlashMap은 Redirect전 session과 같은 장소에 저장한뒤 redirect 후 즉시 삭제
            flashMap.put("message", "로그인 후 이용가능한 서비스입니다.");
            RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flashMap, request, response);

            response.sendRedirect("/");

            return false;
        }
        return true;
    }

}
