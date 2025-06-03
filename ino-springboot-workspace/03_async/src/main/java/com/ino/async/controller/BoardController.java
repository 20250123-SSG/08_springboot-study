package com.ino.async.controller;

import com.ino.async.dto.BoardDto;
import com.ino.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//@Controller
//@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/list", produces="application/json")
    @ResponseBody
    public Map<String, Object> boardlist(int page){
        // jackson 을 통해 Java <-> JSON 간 자동 변환되어 통신함
        // , but GetMapping 내에 produces=를통해 지정해주어야함
        return boardService.getBoardsAndPaging(page);
    }

    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BoardDto boardDetail(int no){
        return boardService.getBoard(no);
    }

    @PostMapping("/regist")
    @ResponseBody
    public Map<String, Object> registBoard(BoardDto board){
        return boardService.registBoard(board);
    }
}
