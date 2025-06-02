package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;
import com.example.demo.domain.repository.BoardRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public BoardEntity saveContent(BoardEntity board) {
        return boardRepository.save(board);
    }

    @Override
    public List<BoardEntity> getAllBoard() {
        return boardRepository.findAll();
    }
}
