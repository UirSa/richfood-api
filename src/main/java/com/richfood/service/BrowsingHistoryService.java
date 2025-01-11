package com.richfood.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.BrowsingHistory;
import com.richfood.repository.BrowsingHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class BrowsingHistoryService {
	
	@Autowired BrowsingHistoryRepository browsingHistoryRepository;
	@Transactional
	 public void addBrowsingHistory(Integer userId, Integer restaurantId) {
		 
		 if (userId == null) {
	            // 若 userId 為空，代表未登入/或 session 無效，則直接 return
	            return;
	        }
		 
		 LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
	        
	        		boolean hasSameRecordRecently = !browsingHistoryRepository
	                .findByUserIdAndRestaurantIdAndViewedAtGreaterThan(userId, restaurantId, oneHourAgo)
	                .isEmpty();
	        		
	        if (hasSameRecordRecently) {
	            // 若 1 小時內有相同紀錄，直接忽略，不新增
	            return;
	        }
	        
	        BrowsingHistory history = new BrowsingHistory();
	        history.setUserId(userId);
	        history.setRestaurantId(restaurantId);
	        history.setViewedAt(LocalDateTime.now());

	        browsingHistoryRepository.save(history);

	        Long count = browsingHistoryRepository.countByUserId(userId);
	        if (count > 6) {

	        // 3) 再 count
	            List<BrowsingHistory> allRecords = browsingHistoryRepository.findByUserIdOrderByViewedAtAsc(userId);
	            if (!allRecords.isEmpty()) {
	                BrowsingHistory oldestRecord = allRecords.get(0);
	                browsingHistoryRepository.delete(oldestRecord); 
	        	}
	        }
	    }
	 
	 public List<BrowsingHistory> getBrowsingHistoryByUserId(Integer userId) {
	        // 假設我們需要用 userId 來查詢
	        
	        return browsingHistoryRepository.findByUserIdOrderByViewedAtAsc(userId);
	    }
	
	
}
