package com.ino.app.controller;

import com.ino.app.dto.BoardDto;
import com.ino.app.dto.UserDto;
import com.ino.app.mapper.BoardMapper;
import com.ino.app.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/*
    boardListService
    - listPage req : GET /board/list.page - param(page=no)

    registBoardService
    - registPage req : GET /board/regist.page
    - registBoard req : POST /board/regist.do - param(forminput, files)

    boardDetailService
    - detailBoardPage req : GET /borad/detail.page - param(no=no)
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @GetMapping("/list.page")
    public void boardListPage(@RequestParam(value = "page", defaultValue = "1") int page
                            , Model model) {
        // requestparam annotation을 통해 받을 값, 기본값 설정
        Map<String, Object> map = boardService.getBoardsAndPaging(page);
        model.addAttribute("page", map.get("page"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("list", map.get("list"));
    }

    @GetMapping("/regist.page")
    public void boardRegistPage(){}

    @PostMapping("/regist.do")
    public String registBoard(BoardDto board
                            , List<MultipartFile> uploadFiles
                            , HttpSession session
                            , RedirectAttributes redirectAttributes){
        int userNo = ((UserDto)session.getAttribute("loginUser")).getUserNo();
        // login user no == writer user no
        board.setBoardWriter(String.valueOf(userNo));
        Map<String, Object> map = boardService.registBoard(board, uploadFiles);
        redirectAttributes.addFlashAttribute("message", map.get("message"));
        return "redirect:/board/list.page";
    }

    @GetMapping("/detail.page")
    public void boardDetailPage(int no, Model model){
        Map<String,Object> map = boardService.getBoardDetail(no);
        model.addAttribute("board", map.get("board"));
        model.addAttribute("list", map.get("list"));
    }
}
