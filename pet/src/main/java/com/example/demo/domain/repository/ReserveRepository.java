package com.example.demo.domain.repository;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.ReserveEntity;
import com.example.demo.domain.entity.SitterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    List<ReserveEntity> findByOwner(OwnerEntity owner);

    List<ReserveEntity> findBySitter(SitterEntity sitter);

    List<ReserveEntity> findByStatusAndDateBefore(String status, LocalDateTime date);

    @Query(value = "SELECT * FROM (" +
            "  SELECT pr.*, ROW_NUMBER() OVER (ORDER BY reserve_id DESC) rn " +
            "  FROM pet_reservation pr" +
            ") WHERE rn BETWEEN :startRow AND :endRow",
            nativeQuery = true)
    List<ReserveEntity> ownerPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

    @Query(value = "SELECT COUNT(*) FROM pet_reservation", nativeQuery = true)
    int countTotalreserve();
}
