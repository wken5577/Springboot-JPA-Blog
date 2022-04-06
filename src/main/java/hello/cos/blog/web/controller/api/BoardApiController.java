package hello.cos.blog.web.controller.api;

import hello.cos.blog.config.auth.PrincipalDetail;
import hello.cos.blog.service.BoardService;
import hello.cos.blog.web.dto.CreateBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<Long> save(@RequestBody CreateBoardDto createBoardDto, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.saveBoard(createBoardDto.getTitle(), createBoardDto.getContent(), principal.getUser());
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }


}
