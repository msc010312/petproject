package com.example.demo.service;

import com.example.demo.domain.entity.BoardEntity;

<<<<<<< HEAD
public interface BoardService {
    BoardEntity saveContent(BoardEntity board);
=======
import java.util.List;

public interface BoardService {
    BoardEntity saveContent(BoardEntity board);
    List<BoardEntity> getAllBoard();
>>>>>>> feature/board
}
