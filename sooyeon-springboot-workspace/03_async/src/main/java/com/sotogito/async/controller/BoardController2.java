package com.sotogito.async.controller;

import com.sotogito.async.dto.BoardDto;
import com.sotogito.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController2 {
    /**
     * ## ResponseEntity
     * 1. 비동기 응답을 위한 클래스
     * 2. 응답의 본문(body), 해더(headers), HTTP 상태코드(Status Code)를 직접 설정할 수 있음
     * 3. REST API 개발에 많이 사용됨
     * 4. @ResponseBody가 자동으로 적용되므로 생략 가능
     */

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@RequestParam int page) {
        Map<String, Object> pageInfo = boardService.getBoardsAndPaging(page);

        //응답 헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        // new ResponseEntity<>(body에 담을 데이터 + 헤더객체 + HTTP상태코드)
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pageInfo); ///Builder 스타일
//        return new ResponseEntity<>(pageInfo, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto> detail(@RequestParam int no) {
        BoardDto board = boardService.getBoard(no);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if(board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(board);
    }

    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody BoardDto board) { /// JSON 등 Body 전체를 객체로 받을 때 (REST API)
        Map<String, Object> insertResult = boardService.registBoard(board);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if((int) insertResult.get("status") == HttpStatus.OK.value()) {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(insertResult);
        } else if ((int) insertResult.get("status") == HttpStatus.BAD_REQUEST.value()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(insertResult);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(insertResult);
        }
    }

}
