package com.richfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.richfood.model.History;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query("SELECT r.restaurantId, COUNT(h.historyId) AS clickCount FROM History h JOIN h.restaurants r WHERE h.viewedAt BETWEEN :startDate AND :endDate GROUP BY r.restaurantId")
    List<Object[]> findClickCounts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT MAX(count) FROM (SELECT COUNT(h.historyId) AS count FROM History h JOIN h.restaurants r WHERE h.viewedAt BETWEEN :startDate AND :endDate GROUP BY r.restaurantId)")
    Double findMaxClickCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
