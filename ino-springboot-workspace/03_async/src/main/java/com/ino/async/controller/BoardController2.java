package com.ino.async.controller;

import com.ino.async.dto.BoardDto;
import com.ino.async.service.BoardService;
import com.ino.async.util.PageUtil;
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

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController2 {

    private final BoardService boardService;
    private final PageUtil pageUtil;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(int page) {

        Map<String, Object> map = boardService.getBoardsAndPaging(page);

        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        // @GetMapping(produces="") 대체 구문
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDto> detail(int no) {
        BoardDto board = null;
        board = boardService.getBoard(no);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (board != null) {
            return new ResponseEntity<>(board, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody BoardDto board){

        Map<String, Object> map = boardService.registBoard(board);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        switch ((int)map.get("status")) {
            case 200: return new ResponseEntity(map, headers, HttpStatus.OK);
            case 400: return new ResponseEntity(map, headers, HttpStatus.BAD_REQUEST);
            default: return new ResponseEntity(map, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
