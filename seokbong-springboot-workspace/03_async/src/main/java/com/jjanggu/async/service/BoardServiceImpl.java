package com.jjanggu.async.service;

import com.jjanggu.async.dto.BoardDto;
import com.jjanggu.async.mapper.BoardMapper;
import com.jjanggu.async.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor // 초기화?를 위해 필요  => 초기화한다 == 서비스에 객체가 주입되어 사용할 준비가 된 상태
@Service
public class BoardServiceImpl implements BoardService {

    // final 필드는 초기화 필요
    private final BoardMapper boardMapper;
    private final PageUtil pageUtil;

    @Override
    public Map<String, Object> getBoardsAndPaging(int page) {
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
    public Map<String, Object> registBoard(BoardDto boards) {

        int status = 0;
        String msg = null;

        if(boards.getTitle().isEmpty() || boards.getContent().isEmpty()){
            status = 400;
            msg = "입력값 문제로 인한 등록 실패";
        }else { // 입력값에 문제가 없을 경우
            try {
                boardMapper.insertBoard(boards);
                status = 200;
                msg = "등록 성공";
            }catch (Exception e){
                e.printStackTrace();
                status = 500;
                msg = "서버 내부 오류로 인한 등록 실패";
            }
        }

        return Map.of("status", status, "msg", msg);
    }
}
