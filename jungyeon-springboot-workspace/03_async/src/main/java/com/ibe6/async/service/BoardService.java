package com.ibe6.async.service;

import com.ibe6.async.dto.BoardDto;


import java.util.Map;

public interface BoardService {

    Map<String, Object> getBoardsAndPaging(int page);
    BoardDto getBoard(int boardId);
    Map<String, Object> registBoard(BoardDto board);

}