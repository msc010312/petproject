package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    BoardEntity saveContent(BoardEntity board);
    List<BoardEntity> getAllBoard();
}
