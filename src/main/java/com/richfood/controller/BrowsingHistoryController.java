package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richfood.model.BrowsingHistory;
import com.richfood.service.BrowsingHistoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/History")
public class BrowsingHistoryController {
	
	@Autowired BrowsingHistoryService browsingHistoryService;
	
	 @PostMapping("/record")
	    public ResponseEntity<String> recordBrowsingHistory(
	            @RequestParam("restaurantId") Integer restaurantId,HttpSession session) {

	        // 假設我們在登入時，將 "userId" 放到 session
	        Integer userId = (Integer) session.getAttribute("userId");
	        
	        // 呼叫 service 寫入紀錄
	        browsingHistoryService.addBrowsingHistory(userId, restaurantId);
	        
	        return ResponseEntity.ok("瀏覽紀錄已寫入");
	    }
	 
	 @GetMapping("/list")
	    public ResponseEntity<?> getBrowsingHistory(HttpSession session) {
	        Integer userId = (Integer) session.getAttribute("userId");
	        if(userId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
	        }
	        
	        List<BrowsingHistory> historyList = browsingHistoryService.getBrowsingHistoryByUserId(userId);
	        
	        return ResponseEntity.ok(historyList);
	    }

}
