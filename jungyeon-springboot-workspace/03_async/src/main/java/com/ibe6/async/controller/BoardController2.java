package com.ibe6.async.controller;


import com.ibe6.async.dto.BoardDto;
import com.ibe6.async.service.BoardService;
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
        ## ResponseEntity ##
        1. 비동기 응답을 위한 클래스
        2. 응답의 본문(body), 헤더(Headers), HTTP 상태코드(Status Code)를 직접 설정할 수 있음
        3. REST API 개발에 많이 사용됨
        4. @ResponseBody 가 자동으로 적용되므로 생략 가능
     */

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(int page){

        Map<String, Object> map = boardService.getBoardsAndPaging(page);

        // 응답 헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json"); // @GetMapping(produces="") 대신하는 구문

        // new ResponseEntity<>(body에담을데이터, 헤더객체, HTTP상태코드)
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto> detail(int no){

        BoardDto board = boardService.getBoard(no);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if(board == null){ // 조회결과없음 => 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{ // 조회결과있음 => 200
            return new ResponseEntity<>(board, headers, HttpStatus.OK);
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody BoardDto board){

        Map<String, Object> map = boardService.registBoard(board); // {status:xx, msg:xx}

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        if((int)map.get("status") == 200){
            return new ResponseEntity<>(map, headers, HttpStatus.OK);
        }else if((int)map.get("status") == 400){
            return new ResponseEntity<>(map, headers, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(map, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }





}