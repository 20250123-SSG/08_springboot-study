package com.sotogito.app.controller;

import com.sotogito.app.dto.BoardDto;
import com.sotogito.app.dto.UserDto;
import com.sotogito.app.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * 게시글목록서비스
 * - 게시글목록페이지요청 : GET /board/list.page - param(page=요청하는페이지번호)
 *
 * 게시글등록서비스
 * - 게시글등록페이지요청 : GET /board/regist.page
 * - 게시글등록요청 : POST /board/regist.do - param(폼입력갑스 첨부파일들)
 *
 * 게시글상세서비스
 * - 게시글상세페이지요청 : GET /board.detail.page - param(no=글번호)
 *
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list.page")
    public void listPage(@RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        Map<String, Object> pageInfo = boardService.getBoarsAndPaging(page);

        model.addAttribute("page", pageInfo.get("page"));
        model.addAttribute("beginPage", pageInfo.get("beginPage"));
        model.addAttribute("endPage", pageInfo.get("endPage"));
        model.addAttribute("totalPage", pageInfo.get("totalPage"));
        model.addAttribute("boards", pageInfo.get("boards"));
    }

    @GetMapping("/regist.page")
    public void boardRegistPage() {
    }

    @PostMapping("/regist.do")
    public String boardRegist(@ModelAttribute BoardDto board,
                              HttpSession session,
                              List<MultipartFile> uploadFiles,
                              RedirectAttributes redirectAttributes) {

        int userNo = ((UserDto) session.getAttribute("loginUser")).getUserNo();
        board.setBoardWriter(String.valueOf(userNo));

        Map<String, Object> boardInfo = boardService.registerBoard(board, uploadFiles);

        redirectAttributes.addFlashAttribute("message", boardInfo.get("message"));

        return "redirect:/board/list.page";
    }

    @GetMapping("/detail.page")
    public String boardDetailPage(@RequestParam int no, Model model) {
        Map<String, Object> boardInfo = boardService.getBoardDetail(no);

        model.addAttribute("board", boardInfo.get("board"));
        model.addAttribute("attachs", boardInfo.get("attachs"));

        return "board/detail";
    }

}
