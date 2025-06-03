package com.kyungbae.app.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

/*
    ## 인터셉터
    1. 특정 Controller측 핸들러 메소드가 실행되기 전, 실행된 후, 뷰렌더링이 완료된 후 시점에 동작시킬 내용을 정의
    2. HandlerInterceptor 를 구현하는 클래스로 정의한 후 시점에 해당하는 메소드를 오버라이딩 하여 재정의 하면 됨
        1) preHandle    : DispatcherServlet 이 요청에 맞는 핸들러메소드(Controller)를 호출하기 전에 동작
        2) postHandle   : 핸들러메소드 (Controller) 에서 요청처리 후 DispatcherServlet 으로 결과가 반환될 때 동작
        3) afterComplete: 뷰 렌더링이 완료된 이후 (클라이언트에 응답이 전송된 후) 에 동작
 */
@Component
public class SigninCheckInterceptor implements HandlerInterceptor {

    /**
     * Interception point before the execution of a handler. Called after
     * HandlerMapping determined an appropriate handler object, but before
     * HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending an HTTP error or writing a custom response.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation returns {@code true}.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // true : 기존 정상흐름대로 Controller 실행
        // false: Controller 실행 안함

        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") == null) { // 로그인이 되어있지 않을 경우
            // RedirectAttribute의 flashAttribute로 메세지 담기
            FlashMap flashMap = new FlashMap();
            flashMap.put("message", "로그인 후 이용가능한 서비스입니다.");
            RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flashMap, request, response);

            response.sendRedirect("/"); // 페이지 재요청
            return false;
        } else { // 로그인이 되어있을 경우
            return true;
        }

    }
}
