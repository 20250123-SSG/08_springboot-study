package com.ino.async.service;

import com.ino.async.dto.BoardDto;
import com.ino.async.mapper.BoardMapper;
import com.ino.async.util.PageUtil;
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
        int totalCnt = boardMapper.selectBoardCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCnt, page, 10, 5);
        List<BoardDto> list = boardMapper.selectBoardList(map);
        map.put("list", list);
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

        if (board.getTitle().isEmpty() || board.getContent().isEmpty()) {
            status = 400;
            msg = "Input prob - regist failed";
        } else {
            try{
                boardMapper.insertBoard(board);
                status = 200;
                msg = "regist successed";
            }catch (Exception e){
                e.printStackTrace();
                status = 500;
                msg = "Server internal Error - regist failed";
            }
        }
        return Map.of("status", status, "message", msg);
    }

}
