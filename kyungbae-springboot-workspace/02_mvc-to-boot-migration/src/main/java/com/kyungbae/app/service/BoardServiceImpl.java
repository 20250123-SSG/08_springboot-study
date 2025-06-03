package com.kyungbae.app.service;

import com.kyungbae.app.dto.AttachDto;
import com.kyungbae.app.dto.BoardDto;
import com.kyungbae.app.mapper.BoardMapper;
import com.kyungbae.app.util.FileUtil;
import com.kyungbae.app.util.PageUtil;
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
    public Map<String, Object> getBoardAndPaging(int page) {
        int totalCount = boardMapper.selectBoardListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 5, 5);
        List<BoardDto> boards = boardMapper.selectBoardList(map);
        map.put("boards", boards);
        return map;
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files) {

        try {
            // 1) 게시글 정보 db insert
            int result = boardMapper.insertBoard(board);
            // 2) 첨부파일 업로드 & 첨부파일 정보 db insert (첨부파일 존재 시)
            if (result == 1 && files != null) { // 파일 존재할 시
                for (MultipartFile file : files) {
                    if (file != null && !file.getOriginalFilename().equals("")) { // 파일 유효성 채크
                        Map<String, String> map = fileUtil.fileupload("board", file); // 각 파일 저장
                        AttachDto attach = AttachDto.builder() // attach table에 담을 내용 생성
                                .filePath(map.get("filePath"))
                                .filesystemName(map.get("filesystemName"))
                                .originalName(map.get("originalFileName"))
                                .refBoardNo(board.getBoardNo())
                                .build();
                        boardMapper.insertAttach(attach); // attach table에 저장
                    }
                }
            }
        } catch (Exception e) { // 게시글 생성 중 예외 발생 시
            e.printStackTrace();
            return Map.of("message", "게시글 등록에 실패하였습니다.");
        }

        return Map.of("message", "게시글이 등록되었습니다.");
    }

    @Override
    public Map<String, Object> getBoardDetail(int no) {
        BoardDto board = boardMapper.selectBoardByNo(no);
        List<AttachDto> attaches = boardMapper.selectAttachByBoardNo(no);

        return Map.of("board", board, "attaches", attaches);
    }
}
