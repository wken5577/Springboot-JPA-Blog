package hello.cos.blog.web.dto;

import hello.cos.blog.model.Board;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardDetailDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long writerId;
    private int count;
    private List<ReplyDto> replyList;


    public BoardDetailDto(Board board) {
        this.writer = board.getUser().getUsername();
        this.writerId =  board.getUser().getId();
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.count = board.getCount();
        this.replyList = board.getReplyList().stream()
                .map(ReplyDto::new).collect(Collectors.toList());
    }
}
