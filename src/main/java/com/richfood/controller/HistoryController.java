package com.richfood.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

	@Autowired
    private HistoryService historyService;

	@PostMapping("/record")
	public ResponseEntity<String> recordHistory(@RequestBody Map<String, Integer> payload) {
	    Integer restaurantId = payload.get("restaurantId");
	    if (restaurantId == null) {
	        return ResponseEntity.badRequest().body("restaurantId is required");
	    }

	    historyService.saveHistory(restaurantId);
	    return ResponseEntity.ok("History recorded successfully");
	}

	
}
