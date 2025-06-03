package com.sotogito.app.config;

import com.sotogito.app.handler.ChatEchoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@RequiredArgsConstructor
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatEchoHandler chatEchoHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatEchoHandler, "/chat") ///  chat 요청시 해당 웹소켓 핸들러가 구동되면서 우베소켓 통신을 할 수 있도록
                .addInterceptors(new HttpSessionHandshakeInterceptor()) ///로그인한 회원의 정보가 담겨있는 HttpSession 사용을 위한 객체 등록
                .withSockJS();  /// 클라이언트에서 웹소켓 통신을 위한 라이브러리 sock.js 사용 설정
    }
}
