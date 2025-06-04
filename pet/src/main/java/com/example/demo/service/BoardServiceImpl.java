package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;
import com.example.demo.domain.repository.BoardRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<BoardEntity> getAllBoard(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return boardRepository.findAll();
    }

    @Override
    public int getTotalBoardCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pet_board", Integer.class);
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
