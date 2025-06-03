package app.migration.service;

import app.migration.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    // 게시글 목록 조회
    Map<String, Object> getBoardsAndPaging(int page); //사용자가 선택한 페이지

    // 게시글 등록
    Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files);

    // 게시글 상세
    Map<String, Object> getBoardDetail(int no); //조회를 위해 식별자
}
