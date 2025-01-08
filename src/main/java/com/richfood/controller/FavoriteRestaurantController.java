package com.richfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.richfood.model.FavoriteRestaurants;

import com.richfood.service.FavoriteRestaurantService;

@Controller
@RequestMapping("/")
public class FavoriteRestaurantController {
	
	@Autowired FavoriteRestaurantService favoriteRestaurantService;
	
	@PostMapping("/addFavoriteRestaurant")
	 public ResponseEntity<FavoriteRestaurants> addFavoriteRestaurant(@RequestBody  FavoriteRestaurants favoriteRestaurant) {
		
		if (favoriteRestaurant.getUserId() == null) {
	        throw new IllegalArgumentException("UserId must not be null");
	    }
		
		FavoriteRestaurants savedFavoriteRestaurant = favoriteRestaurantService.addFavoriteRestaurant(favoriteRestaurant.getUserId(), favoriteRestaurant.getRestaurantId());
	     return ResponseEntity.ok(savedFavoriteRestaurant);
    }
	
	 @PostMapping("/CancelFavorite")
	 public ResponseEntity<String> CancelFavorite(@RequestBody FavoriteRestaurants favoriteRestaurant) {
	        // 切換收藏狀態
	        favoriteRestaurantService.CancelFavorite(favoriteRestaurant.getUserId(), favoriteRestaurant.getRestaurantId());
	        return ResponseEntity.ok("收藏狀態已更新");
	    }

}
