package com.sotogito.app.service;

import com.sotogito.app.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    Map<String, Object> getBoarsAndPaging(int page);

    Map<String, Object> registerBoard(BoardDto board, List<MultipartFile> files);

    Map<String, Object> getBoardDetail(int no);

}
