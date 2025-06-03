package com.younggalee.async.controller;

import com.younggalee.async.dto.BoardDto;
import com.younggalee.async.service.BoardService;
import com.younggalee.async.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController1 {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping(value = "/list", produces = "application/json")  // produces : 응답본문(body)에 담은 데이터의 유형
    public Map<String, Object> list(int page) {
        Map<String, Object> map = boardService.getBoardsAndPaging(page);
        log.info("잘 들어옴 {}" , map);
        return map; // Java(Map 객체) >> JSON문자열을 해줘야하는데 스프링부트의 json라이브러리가 미리 설치되어있어 알아서 변환해준다고 함. 하지만 응답본문에 대한 content type은 저장해주어야함. (getmapping에)
        //비동기방식으로 응답을 하고 싶으면 http통신의 response내에 body에 담으면 됨.

    }
    @ResponseBody
    @GetMapping(value = "/detail" , produces = MediaType.APPLICATION_JSON_VALUE)
    public BoardDto detail(int no){
        return boardService.getBoard(no); // Java(Dto객체) => Json문자열 알아서 변경됨
    }

    @ResponseBody
    @PostMapping(value = "/regist", produces = MediaType.APPLICATION_JSON_VALUE)  //FormData 전송 값은 @RequestParam을 이용해 전달받기
    public Map<String, Object> regist(BoardDto board) {
        return boardService.registBoard(board); // status, msg
    }
}
