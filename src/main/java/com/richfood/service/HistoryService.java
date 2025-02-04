package com.richfood.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.History;
import com.richfood.repository.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
    private HistoryRepository historyRepository;

    // 保存一筆餐廳的點擊紀錄
    public void saveHistory(Integer restaurantId) {
        History history = new History();
        history.setRestaurantId(restaurantId);
        history.setViewedAt(LocalDateTime.now());
        historyRepository.save(history);
    }
    
}
