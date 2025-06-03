package com.ino.app.config;

import com.ino.app.handler.ChatEchoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatEchoHandler chatEchoHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatEchoHandler,"/chat") // /chat 요청시 해당 웹소켓 핸들러 구동되며 통신 가능케하기 위해
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // chatEchoHandler 내 파라미터 webSocketSession객체를 통해 httpsession객체의 값도 가져올 수 있도록
                .withSockJS(); // client web socket 통신 위한 라이브러리 사용 설정
    }
}
