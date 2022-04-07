package hello.cos.blog.service;

import hello.cos.blog.model.Board;
import hello.cos.blog.model.User;
import hello.cos.blog.repository.BoardRepository;
import hello.cos.blog.web.dto.BoardDetailDto;
import hello.cos.blog.web.dto.BoardDto;
import hello.cos.blog.web.dto.IndexBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void saveBoard(String title, String content, User user){
        boardRepository.save(new Board(title,content,user));
    }

    @Transactional(readOnly = true)
    public Page<IndexBoardDto> getAllBoard(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        Page<IndexBoardDto> indexBoardDtos = page.map(IndexBoardDto::new);

        return indexBoardDtos;
    }

    @Transactional(readOnly = true)
    public BoardDetailDto boardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다"));
        return new BoardDetailDto(board);
    }

    public Long deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
        return boardId;
    }

    @Transactional(readOnly = true)
    public BoardDto getDtoById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("글 수정 실패 : 글을 찾을 수 없습니다."));

        return new BoardDto(board);
    }

    public Long update(Long boardId, String title, String content) {
        Board target = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("글 수정 실패 : 글을 찾을 수 없습니다."));
        target.update(title,content);
        return target.getId();
    }
}
