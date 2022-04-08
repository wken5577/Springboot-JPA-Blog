package hello.cos.blog.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.cos.blog.config.auth.PrincipalDetail;
import hello.cos.blog.service.BoardService;
import hello.cos.blog.web.dto.BoardDto;
import hello.cos.blog.web.dto.ReplyContent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<Long> save(@RequestBody BoardDto createBoardDto, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.saveBoard(createBoardDto.getTitle(), createBoardDto.getContent(), principal.getUser());
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }

    @DeleteMapping("/api/board/{boardId}")
    public ResponseEntity<Long> deleteById(@PathVariable Long boardId) {
        Long deletedId = boardService.deleteById(boardId);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    @PutMapping("/api/board/{boardId}")
    public ResponseEntity<Long> update(@PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Long updateId = boardService.update(boardId, boardDto.getTitle(), boardDto.getContent());
        return new ResponseEntity<>(updateId, HttpStatus.OK);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseEntity<Long> replySave(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetail principal,
                                          @RequestBody ReplyContent replyContent) {

        Long replyId = boardService.saveReply(boardId, principal.getUser(), replyContent.getContent());
        return new ResponseEntity<>(replyId, HttpStatus.OK);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseEntity<Long> replyDelete( @PathVariable Long replyId) {
       Long deleteReplyId =  boardService.deleteReply(replyId);
        return new ResponseEntity<>(deleteReplyId, HttpStatus.OK);
    }
}
