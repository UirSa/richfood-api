package com.richfood.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.Reviews;
import com.richfood.repository.ReviewsRepository;

@Service
public class ReviewsService {

	@Autowired ReviewsRepository reviewsRepository;
	
	 /**
     * 取得全部評論（比如管理者撈資料）
     */
    public List<Reviews> getAllReviews() {
    	 return reviewsRepository.findByIsFlaggedFalse();
    }

    /**
     * 根據餐廳 ID 取得評論  (餐廳底下輸出評論)
     */
    public List<Reviews> getReviewsByRestaurantId(Integer restaurantId) {
        return reviewsRepository.findByRestaurantId(restaurantId);
    }

    /**
     * 根據使用者 ID 取得評論  (會員底下輸出自身評論)
     */
    public List<Reviews> getReviewsByUserId(Integer userId) {
        return reviewsRepository.findByUserId(userId);
    }

    /**
     * 新增評論
     */
    public Reviews createReview(Reviews review, Integer currentUserId, Integer restaurantId) {
    	
    	if (reviewsRepository.findByUserIdAndRestaurantId(currentUserId, restaurantId) .isPresent()) {
            throw new RuntimeException("您已對該餐廳發表過評論，無法再次新增");
        }
    	
    	// 1. 指定使用者 (從 Session 拿到的 userId)
        review.setUserId(currentUserId);

        // 2. 指定餐廳 (從路徑拿到的 restaurantId)
        review.setRestaurantId(restaurantId);

        // 3. 預設管理者未查看 → isFlagged = false
        review.setFlagged(false);
        
        // 4. 預設管理者未審核 → IsApproved = false
        review.setApproved(false);
        
        review.setCreatedAt(LocalDateTime.now());
        

        // 5. 儲存至資料庫
        return reviewsRepository.save(review);
    }
    
    /**
     * 更新評論 - 透過 (userId, restaurantId) 找到那筆評論
     */
    public Reviews updateReviewByUserAndRestaurant(Integer userId, Integer restaurantId, Reviews newReviewData) {
        // 查詢該使用者在這家餐廳是否有評論
    	// 從 Optional 中取得 Reviews，若不存在則拋出例外
        Reviews existingReview = reviewsRepository.findByUserIdAndRestaurantId(userId, restaurantId)
            .orElseThrow(() -> new RuntimeException("找不到該評論，或您尚未對此餐廳發表評論"));

        // 如果傳入的評分不為 null，則更新評分
        if (newReviewData.getRating() != null) {
            existingReview.setRating(newReviewData.getRating());
        }

        // 如果傳入的評論內容不為 null，則更新評論內容
        if (newReviewData.getContent() != null) {
            existingReview.setContent(newReviewData.getContent());
        }
        // 儲存更新後的評論
        return reviewsRepository.save(existingReview);
    }

    /**
     * 刪除評論 - 透過 (userId, restaurantId) 找到那筆評論再刪除
     */
    public void deleteReviewByUserAndRestaurant(Integer userId, Integer restaurantId) {
    	Reviews existingReview = reviewsRepository.findByUserIdAndRestaurantId(userId, restaurantId)
                .orElseThrow(() -> new RuntimeException("找不到該評論，或您尚未對此餐廳發表評論"));
        reviewsRepository.delete(existingReview);
    }

	
}
