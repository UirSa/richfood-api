package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.dto.ReviewAuditRequestDTO;
import com.richfood.model.ReviewAudits;
import com.richfood.model.Reviews;
import com.richfood.service.ReviewAuditsService;

@RestController
@RequestMapping("/review-audits")
public class ReviewAuditsController {

    @Autowired
    private ReviewAuditsService reviewAuditsService;
    
    @PostMapping("/review")
    public ResponseEntity<String> reviewAction(@RequestBody ReviewAuditRequestDTO request) {
        // 模擬處理邏輯
    	Integer reviewId = request.getReviewId();
        Integer adminId = request.getAdminId();
        String action = request.getAction();
        String reason = request.getReason();
        Boolean isFinal = request.getIsFinal();

        // 測試打印接收到的值
//        System.out.println("Admin ID: " + adminId);
//        System.out.println("Action: " + action);
//        System.out.println("Reason: " + reason);
//        System.out.println("Is Final: " + isFinal);

        reviewAuditsService.saveReviewAudit(request);
        return ResponseEntity.ok("Request received and processed successfully!");
    }
}
