package com.richfood.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Reviews;
import com.richfood.service.ReviewsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Reviews")
public class ReviewsController {
	
	@Autowired ReviewsService reviewsService;
	
	/*
     * [GET] 取得「該餐廳」的所有評論
     *  GET /Reviews/restaurant/5
     */
    @GetMapping("/restaurant/{restaurantId}")
    public List<Reviews> getReviewsByRestaurantId(@PathVariable Integer restaurantId) {
        return reviewsService.getReviewsByRestaurantId(restaurantId);
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

}
