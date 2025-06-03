package com.ino.app.service;

import com.ino.app.dto.AttachDto;
import com.ino.app.dto.BoardDto;
import com.ino.app.mapper.BoardMapper;
import com.ino.app.util.FileUtil;
import com.ino.app.util.PageUtil;
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
        int totalCnt = boardMapper.selectBoardListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCnt, page, 5, 5);
        List<BoardDto> list = boardMapper.selectBoardList(map);
        map.put("list", list);
        return map;
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board, List<MultipartFile> uploadFiles) {

        try {
            // 1) insert board info
            int result = boardMapper.insertBoard(board);
            // 2) upload file & insert file info (if exists)
            if(result == 1 && uploadFiles != null){
                for(MultipartFile file: uploadFiles){
                    // 파일이 비어있을 수 잇으므로 예외처리 ㅇ
                    if(file != null & !file.getOriginalFilename().equals("")) {
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
            return Map.of("message", "Error: failed to upload board");
        }
        return Map.of("message", "Success: upload successed");
    }

    @Override
    public Map<String, Object> getBoardDetail(int no) {
        BoardDto board = boardMapper.selectBoardByNo(no);
        List<AttachDto> list = boardMapper.selectAttachByBoardNo(no);
        return Map.of("board", board, "list", list);
    }
}
