package com.example.demo.domain.repository;

import com.example.demo.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query(value = "SELECT * FROM (" +
            "  SELECT pb.*, ROW_NUMBER() OVER (ORDER BY created_at DESC) rn " +
            "  FROM pet_board pb" +
            ") WHERE rn BETWEEN :startRow AND :endRow",
            nativeQuery = true)
    List<BoardEntity> findBoardsByPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

    @Query(value = "SELECT COUNT(*) FROM pet_board", nativeQuery = true)
    int countTotalBoards();
}
