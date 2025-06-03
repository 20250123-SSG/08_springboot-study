package com.kyungbae.async.controller;

import com.kyungbae.async.dto.BoardDto;
import com.kyungbae.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController2 {

    private final BoardService boardService;
    /*
        ResponseEntity
        1. 비동기 응답을 위한 클래스
        2. 응답의 본문(body), 헤더(Header), HTTP 상태코드(Status Code)를 직접 설정할 수 있음
        3. REST API 개발에 많이 사용됨
        4. @ResponseBodt 가 자동으로 적용되므로 생략 가능
     */

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(int page){
        Map<String, Object> map =boardService.getBoardsAndPaging(page);
        // 응답헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/JSON");

        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto> detail(int no){
        BoardDto board = boardService.getBoard(no);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if (board == null) { // 조회 결과 없음 => 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else { // 조회 결과 있음 => 200
            return new ResponseEntity<>(board, headers, HttpStatus.OK);
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody BoardDto board){ // JSON 방식으로 전달하는 요소는 무조건 @RequestBody annotaion을 사용해줘야 함
        Map<String, Object> map = boardService.registBoard(board);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if ((int) map.get("status") == 200) {
            return new ResponseEntity<>(map, headers, HttpStatus.OK);
        } else if ((int) map.get("status") == 400) {
            return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(map, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
