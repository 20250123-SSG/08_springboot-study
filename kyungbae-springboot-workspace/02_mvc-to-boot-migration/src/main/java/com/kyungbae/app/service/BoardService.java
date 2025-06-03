package com.kyungbae.app.service;

import com.kyungbae.app.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    // 게시글 목록 조회
    Map<String, Object> getBoardAndPaging(int page/*, int display, String sort*/); // 추가기능: 한 화면에 보여질 항목 개수, 정렬 기준
    // 게시글 등록
    Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files);
    // 게시글 상세
    Map<String, Object> getBoardDetail(int no);
}
