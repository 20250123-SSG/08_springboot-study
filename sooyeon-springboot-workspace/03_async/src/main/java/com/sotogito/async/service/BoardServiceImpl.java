package com.sotogito.async.service;

import com.sotogito.async.dto.BoardDto;
import com.sotogito.async.mapper.BoardMapper;
import com.sotogito.async.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final PageUtil pageUtil;

    @Override
    public Map<String, Object> getBoardsAndPaging(int page) {
        int totalCount = boardMapper.selectBoardCount();
        Map<String, Object> pageInfo = pageUtil.getPageInfo(totalCount, page, 100, 5);

        List<BoardDto> boards = boardMapper.selectBoardList(pageInfo);
        pageInfo.put("boards", boards);

        return pageInfo;
    }

    @Override
    public BoardDto getBoard(int boardId) {
        return boardMapper.selectBoardById(boardId);
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board) {
        if (board.getTitle().trim().isEmpty() || board.getContent().trim().isEmpty()) { //입력값 문제
            return Map.of(
                    "status", 400,
                    "msg", "입력값에 문제로 인한 등록 실패"
            );
        }

        try {
            boardMapper.insertBoard(board);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                    "status", 500,
                    "msg", "내부서버로인한 등록 실패"
            );
        }
        return Map.of(
                "status", 200,
                "msg", "등록성공"
        );
    }

}
