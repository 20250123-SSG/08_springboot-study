package com.ibe6.app.service;

import com.ibe6.app.dto.AttachDto;
import com.ibe6.app.dto.BoardDto;
import com.ibe6.app.mapper.BoardMapper;
import com.ibe6.app.util.FileUtil;
import com.ibe6.app.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final PageUtil pageUtil;
    private final FileUtil fileUtil;

    @Override
    public Map<String, Object> getBoardsAndPaging(int page) {

        int totalCount = boardMapper.selectBoardListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 5, 5);
        List<BoardDto> boards = boardMapper.selectBoardList(map);
        map.put("boards", boards);

        return map; // {beginPage=xx, endPage=xx, ..., boards=[]}
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files) {

        try {
            // 1) 게시글 정보 db insert
            int result = boardMapper.insertBoard(board);
            // 2) 첨부파일 업로드 & 첨부파일 정도 db insert (첨부파일 존재할 경우에만)
            if(result == 1 && files != null){
                for(MultipartFile file : files){
                    if(file != null && !file.getOriginalFilename().equals("")){
                        Map<String, String> map = fileUtil.fileupload("board", file);
                        AttachDto attach = AttachDto.builder()
                                .filePath(map.get("filePath"))
                                .filesystemName(map.get("filesystemName"))
                                .originalName(map.get("originalFilename"))
                                .refBoardNo(board.getBoardNo())
                                .build();
                        boardMapper.insertAttach(attach);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return Map.of("message", "게시글 등록에 실패하였습니다.");

        }
        return Map.of("message", "게시글 등록에 성공하였습니다.");
    }

    @Override
    public Map<String, Object> getBoardDetail(int no) {
        BoardDto board = boardMapper.selectBoardByNo(no);
        List<AttachDto> attachs = boardMapper.selectAttachByBoardNo(no);
        return Map.of();
    }
}
