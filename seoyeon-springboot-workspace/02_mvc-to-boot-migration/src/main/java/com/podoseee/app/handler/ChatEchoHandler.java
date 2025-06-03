package com.podoseee.app.handler;

import com.podoseee.app.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class ChatEchoHandler extends TextWebSocketHandler {

    // 스레드 안전한 리스트로 WebSocket 세션을 관리
    private final List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);

        String userId = ((UserDto)session.getAttributes().get("loginUser")).getUserId();
        String msg = "entry|" + userId + "님이 들어왔습니다.";

        for (WebSocketSession client : sessionList) {
            client.sendMessage(new TextMessage(msg));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String userId = ((UserDto)session.getAttributes().get("loginUser")).getUserId();
        String msg = "chat|" + message.getPayload() + "|" + userId;

        for (WebSocketSession client : sessionList) {
            client.sendMessage(new TextMessage(msg));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);

        String userId = ((UserDto)session.getAttributes().get("loginUser")).getUserId();
        String msg = "exit|" + userId + "님이 퇴장하셨습니다.";

        for (WebSocketSession client : sessionList) {
            client.sendMessage(new TextMessage(msg));
        }
    }
}
