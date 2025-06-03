package com.sotogito.async.controller;

import com.sotogito.async.dto.BoardDto;
import com.sotogito.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController1 {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping(value = "/list", produces = "application/json") //spring에서는 produces 생략해도 되는데 명시 지향
    public Map<String, Object> list(@RequestParam int page) {
        return boardService.getBoardsAndPaging(page); //java(Map객체) => JSON 문자열
    }

    @ResponseBody
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE) //좀 더 지향
    public BoardDto detail(@RequestParam int no) {
        return boardService.getBoard(no); //java(Dto객체) => JSON 문자열
    }

    @ResponseBody
    @PostMapping(value = "/regist", produces = MediaType.APPLICATION_JSON_VALUE) //FormData 전송 값은 @RequestParam을 통해 받음
    public Map<String, Object> regist(@ModelAttribute BoardDto board) {
        return boardService.registBoard(board);
    }

}
