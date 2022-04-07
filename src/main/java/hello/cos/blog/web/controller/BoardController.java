package hello.cos.blog.web.controller;

import hello.cos.blog.service.BoardService;
import hello.cos.blog.web.dto.BoardDetailDto;
import hello.cos.blog.web.dto.BoardDto;
import hello.cos.blog.web.dto.IndexBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<IndexBoardDto> boardDtoPage = boardService.getAllBoard(pageable);
        List<Integer> pageNum = new ArrayList<>();
        for (int i = 0; i < boardDtoPage.getTotalPages(); i++) {
            pageNum.add(i+1);
        }
        model.addAttribute("boardPage", boardDtoPage);
        model.addAttribute("pageNum", pageNum);
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) {
        BoardDetailDto boardDetailDto = boardService.boardDetail(boardId);
        model.addAttribute("board", boardDetailDto);
        return "/board/detail";
    }

    @GetMapping("/board/{boardId}/updateForm")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDto boardDto = boardService.getDtoById(boardId);
        model.addAttribute("board",boardDto);
        return "/board/updateForm";
    }




}
