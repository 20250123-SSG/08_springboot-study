package com.younggalee.async.service;

import com.younggalee.async.dto.BoardDto;

import java.util.Map;

public interface BoardService {
    // Controller : 사용자 input(페이지) 전달 + 서비스 호출
    // Service : 데이터처리를 하여 쿼리에 전달할 데이터 생성하여 전달
    // 따라서 select조회시, 반환값은 쿼리 입력형태, 매개변수는 사용자 input
    Map<String, Object> getBoardsAndPaging(int page);
    BoardDto getBoard(int id);
    Map<String, Object> registBoard(BoardDto boardDto);



}
