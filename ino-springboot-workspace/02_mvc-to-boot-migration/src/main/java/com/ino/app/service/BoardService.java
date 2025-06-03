package com.ino.app.service;

import com.ino.app.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    // 게시글 목록 조회 + 페이징 바 정보(필요하다면 페이지당 몇개를 보여줄건지, 정렬 기준은 어떤 식으로 할 것인지도 고려o,param 추가)
    Map<String, Object> getBoardsAndPaging(int page);
    // 게시글 등록
    Map<String, Object> registBoard(BoardDto board, List<MultipartFile> uploadFiles);
    // detail board
    Map<String, Object> getBoardDetail(int no);
}
