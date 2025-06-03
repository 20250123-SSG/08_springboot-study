package app.migration.controller;

// Service(interface) > ServiceImpl > Controller 순으로 작업

// page : 페이지이동 // do : 액션 (보통)

/*
    게시글목록서비스
    - 게시글목록페이지요청 : GET /board/list.page - param(page=요청하는 페이지번호)
    게시글등록서비스
    - 게시글등록페이지요청 : GET /board/regist.page
    - 게시글등록요청 : POST /board/regist.do - param(폼입력값, 첨부파일들)
    게시글상세서비스
    - 게시글상세페이지요청 : GET  /board/detatil.page - param(no=글번호)

 */

import app.migration.dto.BoardDto;
import app.migration.dto.UserDto;
import app.migration.service.BoardService;
import jakarta.servlet.MultipartConfigElement;
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

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list.page")
    public void boardListPage(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Map<String, Object> map = boardService.getBoardsAndPaging(page);

        model.addAttribute("page", map.get("page"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("boards", map.get("boards"));

        //명시성을 위해 하나씩 넘겨줌 (map 통채로 넘겨도 정상동작)
    }

    @GetMapping("/regist.page")
    public void boardRegistPage(){
        //로그인 여부체크 (인터셉터에서 진행)
    }

    //보통 원래페이지를 다시보여주는 편인데 데이터가 중복 넘어갈 수 있으므로 경로바꿔서 리다이렉트 시켜줌
    @PostMapping("/regist.do")
    public String boardRegist(BoardDto board, HttpSession session  // 작성자의 회원정보를 boardDto에 넣을것임
                                , List<MultipartFile> uploadFiles, RedirectAttributes redirectAttributes){
        int userNo = ((UserDto) session.getAttribute("loginUser")).getUserNo();
        board.setBoardWriter(String.valueOf(userNo));

        Map<String, Object> map = boardService.registBoard(board, uploadFiles);
        // 리다이렉트로 넘겨야하니까
        redirectAttributes.addFlashAttribute(map.get("message"));

        return "redirect:/board/list.page";
    }

    // 기본타입이기에 @RequestParam가 생략되었을 뿐 자동바인딩되어 동작하고 있다.
    @GetMapping("detail.page")
    public void boardDetailPage(int no, Model model){ //이건 RdquestParam 안하나?

        Map<String, Object> map = boardService.getBoardDetail(no);
        model.addAttribute("board", map.get("board"));
        model.addAttribute("attachs", map.get("attachs"));
    }


















}
