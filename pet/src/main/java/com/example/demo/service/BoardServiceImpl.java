package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;
import com.example.demo.domain.repository.BoardRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public BoardEntity saveContent(BoardEntity board) {
        return boardRepository.save(board);
    }

    private final int PAGE_SIZE = 7;

    @Override
    public List<BoardEntity> getAllBoard(int page) {
        int startRow = page * PAGE_SIZE + 1;
        int endRow = (page + 1) * PAGE_SIZE;
        return boardRepository.findBoardsByPage(startRow, endRow);
    }

    @Override
    public int getTotalBoardCount() {
        return boardRepository.countTotalBoards();
    }

    @Override
    public BoardEntity viewBoardContent(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
        return board;
    }


    @Override
    public void deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
