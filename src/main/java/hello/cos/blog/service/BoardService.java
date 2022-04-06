package hello.cos.blog.service;

import hello.cos.blog.model.Board;
import hello.cos.blog.model.User;
import hello.cos.blog.repository.BoardRepository;
import hello.cos.blog.web.dto.IndexBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void saveBoard(String title, String content, User user){
        boardRepository.save(new Board(title,content,user));
    }

    public Page<IndexBoardDto> getAllBoard(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        Page<IndexBoardDto> indexBoardDtos = page.map(IndexBoardDto::new);

        return indexBoardDtos;
    }

}
