package com.podoseee.app.service;

import com.podoseee.app.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    // 게시글 목록 조회
    Map<String, Object> getBoardsAndPaging(int page);
    // 게시글 등록
    Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files);
    // 게시글 상세
    Map<String, Object> getBoardDetail(int no);






}
