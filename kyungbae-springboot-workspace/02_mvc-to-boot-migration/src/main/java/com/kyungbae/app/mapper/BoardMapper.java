package com.kyungbae.app.mapper;

import com.kyungbae.app.dto.AttachDto;
import com.kyungbae.app.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    int selectBoardListCount();
    List<BoardDto> selectBoardList(Map<String, Object> map);
    int insertBoard(BoardDto board);
    int insertAttach(AttachDto attachDto);
    BoardDto selectBoardByNo(int no);
    List<AttachDto> selectAttachByBoardNo(int no);
}
