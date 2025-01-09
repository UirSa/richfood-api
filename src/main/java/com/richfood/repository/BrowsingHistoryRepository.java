package com.richfood.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richfood.model.BrowsingHistory;

@Repository
public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Integer> {
	
	List<BrowsingHistory> findByUserIdOrderByViewedAtAsc(Integer userId);
	
	   // 2) 查詢特定 userId 擁有的紀錄總數
    Long countByUserId(Integer userId);

    // 3) 查詢 userId + restaurantId，且 viewedAt 在指定時間之後的紀錄 (用於判斷「1 小時內是否重複」)
    List<BrowsingHistory> findByUserIdAndRestaurantIdAndViewedAtGreaterThan(
        Integer userId,
        Integer restaurantId,
        LocalDateTime viewedAt
    );

}