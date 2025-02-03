package com.richfood.repository;

import com.richfood.model.Reviews;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
	
	 // 以 userId 和 restaurantId 尋找唯一評論
	
	Optional<Reviews> findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
	
	 // 根據餐廳 ID 查詢
    List<Reviews> findByRestaurantId(Integer restaurantId);

    // 根據使用者 ID 查詢
    List<Reviews> findByUserId(Integer userId);
    
    List<Reviews> findByIsFlaggedFalse();
    
    
    List<Reviews> findByIsFlaggedTrue();
	
	@Query("SELECT r.restaurantId FROM Reviews r WHERE r.id = :reviewId")
	Integer findRestaurantIdByReviewId(@Param("reviewId") Integer reviewId);
	
	List<Reviews> findByIsFlaggedTrueAndIsApprovedTrue();
	
	  // 查詢最新的評論
	@Query("SELECT r.image FROM Restaurants r WHERE r.restaurantId = :restaurantId")
	String findImageByRestaurantId(@Param("restaurantId") Integer restaurantId);
	
	@Query("SELECT r FROM Reviews r ORDER BY r.createdAt DESC")
	List<Reviews> findLatestReviews(Pageable pageable);
	



}
