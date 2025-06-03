package com.ibe6.async.controller;

import com.ibe6.async.dto.BoardDto;
import com.ibe6.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;

@RequiredArgsConstructor
//@RequestMapping("/board")
//@Controller
public class BoardController1 {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping(value="/list", produces="application/json") // produces : 응답본문(body)에 담은 데이터의 유형(Content-Type)
    public Map<String, Object> list(int page){
        return boardService.getBoardsAndPaging(page); // Java(Map객체) => JSON문자열'{"totalCount":"xx", "beginPage":xx, ..., "boards":[{}, {}, ..]}'
    }

    @ResponseBody
    @GetMapping(value="/detail", produces= MediaType.APPLICATION_JSON_VALUE)
    public BoardDto detail(int no){
        return boardService.getBoard(no); // Java(Dto객체) => JSON문자열 {"필드명":"필드값", ..}
    }

    @ResponseBody
    @PostMapping(value="/regist", produces=MediaType.APPLICATION_JSON_VALUE) // FormData 전송 값은 @RequestParam을 이용해 전달받기
    public Map<String, Object> regist(BoardDto board){
        return boardService.registBoard(board); // {status:xx, msg:xx}
    }


}