package com.richfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.dto.ReviewAuditRequestDTO;
import com.richfood.model.ReviewAudits;
import com.richfood.model.Reviews;
import com.richfood.repository.ReviewAuditsRepository;
import com.richfood.repository.ReviewsRepository;

@Service
public class ReviewAuditsService {
	
	@Autowired
    private ReviewAuditsRepository reviewAuditsRepository;

    @Autowired
    private ReviewsService reviewsService;
    
    @Autowired
    private ReviewsRepository reviewsRepository;


    public void saveReviewAudit(ReviewAuditRequestDTO request) {
    	// 創建 ReviewAudits 實體
        ReviewAudits reviewAudit = new ReviewAudits();
        

        // 設置屬性
        reviewAudit.setAdminId(request.getAdminId());
        reviewAudit.setAction(request.getAction());
        reviewAudit.setReason(request.getReason());
        reviewAudit.setFinal(request.getIsFinal());

        // 如果 action 是 "不通過"，刪除 Reviews 表中的對應評論
        if ("不通過".equalsIgnoreCase(request.getAction())) {
            Integer reviewId = request.getReviewId(); // 確保 request 中有 reviewId
            if (reviewId != null && reviewsRepository.existsById(reviewId)) {
            	reviewsRepository.deleteById(reviewId);
            } else {
                throw new RuntimeException("Review not found with ID: " + reviewId);
            }
        }

        // 將數據保存到 ReviewAudits 資料表
        reviewAuditsRepository.save(reviewAudit);
    }
    
}
