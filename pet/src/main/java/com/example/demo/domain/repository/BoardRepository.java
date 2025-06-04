package com.example.demo.domain.repository;

import com.example.demo.domain.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    Page<BoardEntity> findAllByOrderByCreatedAtDesc(Long boardId, Pageable pageable);
}
