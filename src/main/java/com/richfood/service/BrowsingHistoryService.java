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
	
	@Autowired
	private BrowsingHistoryRepository browsingHistoryRepository;

	@Transactional
	public void addBrowsingHistory(Integer userId, Integer restaurantId) {
		if (userId == null || restaurantId == null) {
			// 若 userId 或 restaurantId 為空，直接 return
			return;
		}

		// 查找是否已有該用戶對該餐廳的瀏覽紀錄
		BrowsingHistory existingHistory = browsingHistoryRepository
			.findTopByUserIdAndRestaurantIdOrderByViewedAtDesc(userId, restaurantId);

		if (existingHistory != null) {
			// 如果已有紀錄，則更新瀏覽時間
			existingHistory.setViewedAt(LocalDateTime.now());
			browsingHistoryRepository.save(existingHistory);
		} else {
			// 如果沒有紀錄，則新增一條紀錄
			BrowsingHistory newHistory = new BrowsingHistory();
			newHistory.setUserId(userId);
			newHistory.setRestaurantId(restaurantId);
			newHistory.setViewedAt(LocalDateTime.now());
			browsingHistoryRepository.save(newHistory);
		}

		// 限制用戶的瀏覽紀錄數量最多為 6
		Long count = browsingHistoryRepository.countByUserId(userId);
		if (count > 6) {
			// 如果超過 6 條紀錄，刪除最舊的紀錄
			List<BrowsingHistory> allRecords = browsingHistoryRepository.findByUserIdOrderByViewedAtAsc(userId);
			if (!allRecords.isEmpty()) {
				BrowsingHistory oldestRecord = allRecords.get(0);
				browsingHistoryRepository.delete(oldestRecord);
			}
		}
	}

	public List<BrowsingHistory> getBrowsingHistoryByUserId(Integer userId) {
		// 按瀏覽時間倒序排列，查詢用戶的瀏覽紀錄
		return browsingHistoryRepository.findByUserIdOrderByViewedAtDesc(userId);
	}
}
