package com.kyungbae.app.controller;

import com.kyungbae.app.dto.BoardDto;
import com.kyungbae.app.dto.UserDto;
import com.kyungbae.app.service.BoardService;
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
    게시글 목록 서비스
    - 게시글목록페이지요청
        /board/list.page - param(page=요청하는페이지번호)

    게시글 등록 서비스
    - 게시글등록페이지요청
        GET /board/regist.page
    - 게시글동록요청
        POST /board/regist.do - param(폼입력값, 첨부파일들)

    게시글 상세 서비스
    - 게시글상세페이지요청
        GET /board/detail.page - param(no=글번호)
 */

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list.page")
    public void boardListPage(
            @RequestParam(value="page", defaultValue="1")int page,
            Model model){
        Map<String, Object> map = boardService.getBoardAndPaging(page);
        model.addAttribute("page", map.get("page"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("boards", map.get("boards"));
    }

    @GetMapping("regist.page")
    public void boardRegistPage(){}

    @PostMapping("/regist.do")
    public String boardRegist(
            BoardDto board,
            HttpSession session,
            List<MultipartFile> uploadFiles,
            RedirectAttributes redirectAttributes){

        int userNo = ((UserDto)session.getAttribute("loginUser")).getUserNo();
        board.setBoardWriter(String.valueOf(userNo));

        Map<String, Object> map = boardService.registBoard(board, uploadFiles);

        redirectAttributes.addFlashAttribute("message", map.get("message"));

        return "redirect:/board/list.page";
    }

    @GetMapping("/detail.page")
    public void boardDetailPage(int no, Model model){
        Map<String, Object> map = boardService.getBoardDetail(no);
        model.addAttribute("board", map.get("board"));
        model.addAttribute("attaches", map.get("attaches"));
    }

}
