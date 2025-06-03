package com.jjanggu.async.controller;

import com.jjanggu.async.dto.BoardDto;
import com.jjanggu.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
//@RequestMapping("/board")
//@Controller
public class BoardController1 {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping(value = "/list", produces = "application/json") // produces : 응답본문(body)에 담은 데이터의 유형 // 자바에서는 기본적으로 지정을 해주지만 개발환경이 바뀔 경우도 있기때문에 지정해주는 것이 좋다
    public Map<String, Object> list(int page){
        return boardService.getBoardsAndPaging(page); //Java(Map객체) => JSON문자열 {totalCount, beginPage, ..., boards}
    }

    @ResponseBody
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE) // MediaType.APPLICATION_JSON_VALUE == "application/json"
    public BoardDto detail(int no){
        return boardService.getBoard(no); // Java(Dto객체) => JSON문자열
    }

    @ResponseBody
    @PostMapping(value = "/regist", produces = MediaType.APPLICATION_JSON_VALUE) // FormData 전송 값은 @RequestParam을 이용해 전달받기
    public Map<String, Object> regist(BoardDto board){
        return boardService.registBoard(board); // {status:xx, msg:xx}
    }

}
