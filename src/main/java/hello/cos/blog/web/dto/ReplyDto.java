package hello.cos.blog.web.dto;

import hello.cos.blog.model.Reply;
import lombok.Data;

@Data
public class ReplyDto {

    private Long id;
    private String content;
    private String username;
    private Long writerId;

    public ReplyDto(Reply reply) {
        this.id = reply.getId();
        this.writerId = reply.getUser().getId();
        this.content = reply.getContent();
        this.username = reply.getUser().getUsername();
    }

}
