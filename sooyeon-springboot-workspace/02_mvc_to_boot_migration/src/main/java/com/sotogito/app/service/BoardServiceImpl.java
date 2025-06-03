package com.sotogito.app.service;

import com.sotogito.app.dto.AttachDto;
import com.sotogito.app.dto.BoardDto;
import com.sotogito.app.mapper.BoardMapper;
import com.sotogito.app.util.FileUtil;
import com.sotogito.app.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final PageUtil pageUtil;
    private final FileUtil fileUtil;

    @Override
    public Map<String, Object> getBoarsAndPaging(int page) {
        int totalCount = boardMapper.selectBoardListCount();
        Map<String, Object> pageInfo = pageUtil.getPageInfo(totalCount, page, 5, 5);

        List<BoardDto> boards = boardMapper.selectBoardList(pageInfo);
        pageInfo.put("boards", boards);

        return pageInfo;
    }

    @Override
    public Map<String, Object> registerBoard(BoardDto board, List<MultipartFile> files) {
        /**
         * 1. 게시글 정보 db insert
         * 2. 첨부파일 업로드 & 첨부파일 정보 db insert, 첨부파일이 존재할 경우에만
         */
        try{
           int result = boardMapper.insertBoard(board);
           if(result == 1 && files != null) {

               for (MultipartFile file : files) {

                   if(file != null && !file.getOriginalFilename().equals("")) {
                       Map<String, String> fileInfo = fileUtil.fileupload("board", file);

                       AttachDto attach = AttachDto.builder()
                               .filePath(fileInfo.get("filePath"))
                               .filesystemName(fileInfo.get("filesystemName"))
                               .originalName(fileInfo.get("originalFilename"))
                               .refBoardNo(board.getBoardNo())
                               .build();
                       boardMapper.insertAttach(attach);
                   }
               }
           }
        }catch (Exception e){
            e.printStackTrace();
            return Map.of("message", "게시글 등록 실패");
        }

        return Map.of("message", "게시글 등록 성공");
    }

    @Override
    public Map<String, Object> getBoardDetail(int no) {
        BoardDto board = boardMapper.selectBoardByNo(no);
        List<AttachDto> attachs = boardMapper.selectAttachByBoardNo(no);

        return Map.of(
                "board", board,
                "attachs", attachs
        );
    }

}
