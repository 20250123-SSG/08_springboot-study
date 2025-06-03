package com.jjanggu.app.handler;

import com.jjanggu.app.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChatEchoHandler extends TextWebSocketHandler { // 메세지 수신/전송 로직을 작성

    // 웹소켓세션(클라이언트)들을 저장해두는 리스트
    private List<WebSocketSession> sessionList = new ArrayList<>();

    /**
     * 1) afterConnectionEstablished : 클라이언트와 연결되었을 때 실행되는 메소드
     *
     * @param session    - 현재 연결된 클라이언트 객체 (각각 고유한 랜덤 아이디를 가지고 있음)
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);

        for(WebSocketSession client : sessionList){
            String msg = "entry|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId() + "님이 들어왔습니다.";
            client.sendMessage(new TextMessage(msg));
        }

    }

    /**
     * 2) handleMessage : 해당 웹소켓 측으로 데이터(텍스트, 파일 등)가 전송되었을 경우 실행되는 메소드
     *
     * @param session   - 현재 웹소켓 측으로 데이터를 전송한 클라이언트 객체
     * @param message   - 현재 웹소켓 측으로 전송한 데이터에 대한 정보를 가지고 있는 객체
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        for(WebSocketSession client : sessionList){
            // 메세지유형(chat,entry,exit)|채팅메세지|발신자아이디
            String msg = "chat|" + message.getPayload() + "|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId();
            client.sendMessage(new TextMessage(msg)); // * 클라이언트 측의 onMessage 함수 실행됨
        }

    }

    /**
     * 3) afterConnectionClosed : 클라이언트와 연결이 끊겼을 때 실행되는 메소드
     *
     * @param session   - 현재 웹소켓과 연결이 끊긴 클라이언트 객체
     * @param status    - 종료 상태 코드와 이유 등의 정보를 가지고 있는 객체
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);

        for(WebSocketSession client : sessionList){
            String msg = "exit|" + ((UserDto)session.getAttributes().get("loginUser")).getUserId() + "님이 나갔습니다.";
            client.sendMessage(new TextMessage(msg));
        }

    }
}