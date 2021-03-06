package hello.cos.blog.web.dto;

import hello.cos.blog.model.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IndexBoardDto {
    private String title;
    private Long id;

    public IndexBoardDto(Board board) {
        this.title = board.getTitle();
        this.id = board.getId();
    }
}
