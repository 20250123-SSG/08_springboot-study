package com.podoseee.async.service;

import com.podoseee.async.dto.BoardDto;
import com.podoseee.async.mapper.BoardMapper;
import com.podoseee.async.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final PageUtil pageUtil;

    @Override
    public Map<String, Object> getBoardsAndPaging(int page){

        int totalCount = boardMapper.selectBoardCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 10, 5);
        List<BoardDto> boards = boardMapper.selectBoardList(map);
        map.put("boards", boards);

        return map;
    }

    @Override
    public BoardDto getBoard(int boardId) {
        return boardMapper.selectBoardById(boardId);
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board) {
        int status = 0;
        String msg = null;

        try {
            if (board.getTitle().isEmpty() || board.getContent().isEmpty()) {
                status = 400;
                msg = "입력값 문제로 인한 등록 실패";
            } else {
                boardMapper.insertBoard(board);
                status = 200;
                msg = "등록 성공";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = 500;
            msg = "서버 내부 오류로 인한 등록 실패";
        }

        return Map.of("status", status, "msg", msg);
    }

}
