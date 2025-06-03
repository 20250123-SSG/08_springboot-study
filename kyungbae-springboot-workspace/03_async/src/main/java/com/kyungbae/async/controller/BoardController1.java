package com.kyungbae.async.controller;

import com.kyungbae.async.dto.BoardDto;
import com.kyungbae.async.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
/*@RequestMapping("/board")
@Controller*/
public class BoardController1 {

    private final BoardService boardService;

//    @ResponseBody
//    @GetMapping(value="/list", produces="application/JSON")
    public Map<String, Object> list(int page){
        // java(Map객체) => JSON문자열 [JACKSON = AutoParsing]
        return boardService.getBoardsAndPaging(page);
    }

/*    @ResponseBody
    @GetMapping(value="detail", produces = "application/JSON")*/
    public BoardDto detail(int no){
        return boardService.getBoard(no);
    }

/*    @ResponseBody
    @PostMapping(value="/regist", produces = MediaType.APPLICATION_JSON_VALUE) // FormData 전송 값은 @RequestParam을 이용해 전달받기*/
    public Map<String, Object> regist(BoardDto board){
        return boardService.registBoard(board);
    }
}
