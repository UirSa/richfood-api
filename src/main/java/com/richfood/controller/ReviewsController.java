package com.richfood.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Reviews;
import com.richfood.repository.UsersRepository;
import com.richfood.service.ReviewsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Reviews")
public class ReviewsController {
	
	@Autowired ReviewsService reviewsService;
	
	@Autowired
    private UsersRepository usersRepository;
	
	/* 
     * [GET] 取得「該餐廳」的所有評論
     *  GET /Reviews/restaurant/5
     */
	@GetMapping("/restaurant/{restaurantId}")
	public List<Map<String, Object>> getReviewsByRestaurantId(@PathVariable Integer restaurantId) {
	    // 從 Service 獲取評論
	    List<Reviews> reviews = reviewsService.getReviewsByRestaurantId(restaurantId);

	    // 生成返回結果，添加用戶名稱
	    return reviews.stream().map(review -> {
	        Map<String, Object> result = new HashMap<>();
	        result.put("reviewId", review.getReviewId());
	        result.put("userId", review.getUserId());
	        result.put("userName", usersRepository.findUserNameByUserId(review.getUserId())); // 根據用戶ID獲取用戶名稱
	        result.put("restaurantId", review.getRestaurantId());
	        result.put("rating", review.getRating());
	        result.put("content", review.getContent());
	        result.put("createdAt", review.getCreatedAt());
	        return result;
	    }).collect(Collectors.toList());
	}

    /*
     * [GET] 取得「某使用者」的所有評論
     *  GET /Reviews/user/5
     * - 通常可用於後台管理 (管理者想看某用戶評論)
     * - 或用於前台時，查看自己 
     */
    @GetMapping("/user/{userId}")
    public List<Reviews> getReviewsByUserId(@PathVariable Integer userId) {
        return reviewsService.getReviewsByUserId(userId);
    }
    
    /*
     * [GET] 取得「目前登入使用者」的所有評論
     * 
     *  GET /Reviews/myReviews
     */
    @GetMapping("/myReviews")
    public List<Map<String, Object>> getMyReviews(HttpServletRequest request) {
        // 從 Session 取得 userId
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        if (currentUserId == null) {
            throw new RuntimeException("您尚未登入");
        }
        return reviewsService.getReviewsByUserIdWithRestaurant(currentUserId);
    }
    
    /**
     * [POST] 新增評論
     * - 需要使用者已登入，必須登入才能進行
     *  POST    /Reviews/restaurant/5
     */
    @PostMapping("/restaurant/{restaurantId}")
    public Reviews createReviewForRestaurant(
            HttpServletRequest request, 
            @PathVariable Integer restaurantId,
            @RequestBody Reviews review
    ) {
        // 1. 先確認使用者是否已登入
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        if (currentUserId == null) {
            throw new RuntimeException("您尚未登入，無法發表評論");
        }
        if (review.getRating() == null || review.getContent() == null) {
            throw new RuntimeException("評論內容與評分為必填項");
        }

        // 2. 呼叫 Service 進行建立
        return reviewsService.createReview(review, currentUserId, restaurantId);
    }
    
    /**
     * [PUT] 修改評論
     * - 由 (Session中的userId) + (PathVariable的restaurantId) 決定要改哪條評論
     *  PUT /Reviews/restaurant/10
     */
    @PutMapping("/restaurant/{restaurantId}")
    public Reviews updateReviewByRestaurant(
            HttpServletRequest request,
            @PathVariable Integer restaurantId,
            @RequestBody Reviews review
    ) {
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        if (currentUserId == null) {
            throw new RuntimeException("您尚未登入，無法修改評論");
        }
        return reviewsService.updateReviewByUserAndRestaurant(currentUserId, restaurantId, review);
    }

    /**
     * [DELETE] 刪除評論
     * - 由 (Session中的userId) + (PathVariable的restaurantId) 決定要刪哪條評論
     *  DELETE   /Reviews/restaurant/10
     */
    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteReviewByRestaurant(
            HttpServletRequest request,
            @PathVariable Integer restaurantId
    ) {
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        if (currentUserId == null) {
            throw new RuntimeException("您尚未登入，無法刪除評論");
        }
        reviewsService.deleteReviewByUserAndRestaurant(currentUserId, restaurantId);
    }
    
    
    @GetMapping("/flagged")
    public ResponseEntity<List<Map<String, Object>>> getFlaggedReviews() {
        // 獲取所有被標記 (flagged) 的 Reviews
        List<Reviews> flaggedReviews = reviewsService.getFlaggedReviews();

        // 創建一個列表，用來存放添加了餐廳名稱的 Reviews 信息
        List<Map<String, Object>> responseList = new ArrayList<>();
        
        for (Reviews review : flaggedReviews) {
            // 根據每個 reviewId 獲取餐廳名稱
            String restaurantName = reviewsService.getRestaurantNameByReviewId(review.getReviewId());
            
            // 根據 userId 獲取用戶名稱
            String userName = usersRepository.findUserNameByUserId(review.getUserId());

            // 將 Reviews 轉換成 Map，並添加餐廳名稱
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("reviewId", review.getReviewId());
            reviewMap.put("restaurantId", review.getRestaurantId());
            reviewMap.put("userId", review.getUserId());
            reviewMap.put("userName", userName);
            reviewMap.put("rating", review.getRating());
            reviewMap.put("content", review.getContent());
            reviewMap.put("createdAt", review.getCreatedAt());
            reviewMap.put("storeId", review.getStoreId());
            reviewMap.put("flagged", review.getFlagged());
            reviewMap.put("approved", review.getApproved());
            reviewMap.put("restaurantName", restaurantName); // 新增餐廳名稱

            // 添加到結果列表
            responseList.add(reviewMap);
        }

        return ResponseEntity.ok(responseList);
    }
    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {
        reviewsService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/latest")
    public ResponseEntity<List<Map<String, Object>>> getLatestReviews() {
        return ResponseEntity.ok(reviewsService.getLatestReviews(10)); // 取得最新10條評論
    }
    
    @GetMapping("/restaurant/{restaurantId}/user/{userId}")
    public ResponseEntity<Reviews> getReviewByUserAndRestaurant(
            @PathVariable Integer restaurantId,
            @PathVariable Integer userId
    ) {
        Reviews review = reviewsService.getReviewByUserAndRestaurant(restaurantId, userId);
        if (review == null) {
            return ResponseEntity.notFound().build(); // 找不到該評論，回傳 404
        }
        return ResponseEntity.ok(review);
    }



    
}
