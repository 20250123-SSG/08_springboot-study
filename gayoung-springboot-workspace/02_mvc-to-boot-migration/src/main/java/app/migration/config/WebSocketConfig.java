package app.migration.config;

import app.migration.handler.ChatEchoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatEchoHandler chatEchoHandler;


    /*
    websocket은 처음에 http 요청으로 연결을 시작하고 websocket 프로토콜로 전환한다고 함.
    그리고 이것을 * WebSocket HandShake * 라고 함

    addInterceptors는 웹소켓핸들러 등록시 인터셉터를 추가할 수 있는 메소드
    httpsessionhandshakeInterceptor는 기존의 http세션 정보를 websocket세션으로 복사해 사용할 수 있게 해주는 인터셉터
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatEchoHandler ,"/chat")  //  /chat 요청시 해당 웹소켓 핸들러가 구동되면서 웹소켓 통신을 할 수 있도록
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // 로그인한 회원 정보가 담겨있는 HttpSession 사용을 위한 객체 등록
                .withSockJS();    // 클라이언트에서 웹소켓 통신을 위한 라이브러리 sockjs 사용 설정

    }
}













