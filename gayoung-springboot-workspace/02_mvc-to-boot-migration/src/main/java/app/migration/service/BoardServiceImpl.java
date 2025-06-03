//mapper 파일 나란히 두고 작업하기
package app.migration.service;

import app.migration.dto.AttachDto;
import app.migration.dto.BoardDto;
import app.migration.mapper.BoardMapper;
import app.migration.util.FileUtil;
import app.migration.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final PageUtil pageUtil; // int totalCount, int page, int display, int pagePerBlock
    private final FileUtil fileUtil;

    @Override
    public Map<String, Object> getBoardsAndPaging(int page) {
        int totalCount = boardMapper.selectBoardListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 5, 5);

        // 게시글 조회
        List<BoardDto> boards = boardMapper.selectBoardList(map); // offset, display 필요함
        map.put("boards", boards);

        return map; // 변경불가 -> if : controller에서 변경이 필요하다면 hashmap + put으로 넘겨줘야함.
    }

    @Override
    public Map<String, Object> registBoard(BoardDto board, List<MultipartFile> files) {

        try {
            // 1) 게시글 정보 db insert
            int result = boardMapper.insertBoard(board);
            // 2) 첨부파일 업로드 & 첨부파일 정보 db insert (첨부파일 존재할 경우에만)
            if(result == 1 &&  files != null){
                for(MultipartFile file : files){
                    if(file != null && !file.getOriginalFilename().equals("")){
                        Map<String, String> map = fileUtil.fileupload("board", file);
                        AttachDto attach = AttachDto.builder()
                                .filePath(map.get("filePath"))
                                .filesystemName(map.get("filesystemName"))
                                .originalName(map.get("originalFileName"))
                                .refBoardNo(board.getBoardNo())
                                .build();
                        boardMapper.insertAttach(attach);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("message", "게시글 등록에 실패햐였습니다.");
        }

        return Map.of("message", "게시글 등록에 성공햐였습니다.");
    }

    @Override
    public Map<String, Object> getBoardDetail(int no) {
        BoardDto board = boardMapper.selectBoardByNo(no);
        List<AttachDto> attachs = boardMapper.selectAttachByBoardNo(no);
        return Map.of("board", board, "attachs", attachs);
    }
}
