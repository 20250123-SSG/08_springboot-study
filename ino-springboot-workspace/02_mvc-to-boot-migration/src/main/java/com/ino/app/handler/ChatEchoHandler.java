package com.ino.app.handler;

import com.ino.app.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChatEchoHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessionList = new ArrayList<>();

    /**
     * afterConnectionEstablished : 클라이언트와 연결시 실행
     * @param session       - 현재 연결 클라이언트 관련 객체 ( 클라이언트별 고유 랜덤아이디 보유 )
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);

        for(WebSocketSession client : sessionList){
            String msg = "entry|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId() + "님이 입장하셨습니다.";
            client.sendMessage(new TextMessage(msg));
        }
    }

    /**
     * handleMessage : 해당 웹소켓 측으로 데이터 전송시 실행
     * @param session - 현재 웹소켓 측으로 데이터 전송 클라이언트 객체
     * @param message - 현재 웹소켓 측으로 전송한 데이터에 대한 정보를 가진 객체
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        for(WebSocketSession client : sessionList){
            // msgType|chattingmsg|senderId
            String msg = "chat|" + message.getPayload() + "|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId();
            client.sendMessage(new TextMessage(msg)); // run onMessage function on client-side
        }
    }

    /**
     * afterConnectionClosed : 클라이언트와 연결이 끊길 경우 실행
     * @param session - 현재 웹소켓과 연결이 끊긴 클라이언트 객체
     * @param status - 종료 상태 코드와 이유 등 정보 갖는 객체
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        for(WebSocketSession client : sessionList){
            String msg = "exit|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId() + "님이 퇴장하셨습니다.";
            client.sendMessage(new TextMessage(msg));
        }
    }
}
