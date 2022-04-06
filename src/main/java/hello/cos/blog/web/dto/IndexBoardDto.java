package hello.cos.blog.web.dto;

import hello.cos.blog.model.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IndexBoardDto {
    String title;

    public IndexBoardDto(Board board) {
        this.title = board.getTitle();
    }
}
