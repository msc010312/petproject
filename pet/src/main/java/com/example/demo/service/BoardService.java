package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;

import java.util.List;

public interface BoardService {
    BoardEntity saveContent(BoardEntity board);
    BoardEntity viewBoardContent(Long boardId);
    List<BoardEntity> getAllBoard(int page);
    public int getTotalBoardCount();
    public void deleteById(Long boardId);
}
