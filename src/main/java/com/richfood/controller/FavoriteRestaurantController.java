package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richfood.model.FavoriteRestaurants;
import com.richfood.model.Restaurants;
import com.richfood.service.FavoriteRestaurantService;

@Controller
@RequestMapping("/Favorite")
public class FavoriteRestaurantController {
	
	@Autowired FavoriteRestaurantService favoriteRestaurantService;
	
	@PostMapping("/addFavorite")
	public ResponseEntity<String> addFavorite(@RequestBody FavoriteRestaurants favoriteRestaurant) {
	    favoriteRestaurantService.addFavorite(favoriteRestaurant.getUserId(), favoriteRestaurant.getRestaurantId());
	    return ResponseEntity.ok("餐廳已加入收藏");
	}
	
	@DeleteMapping("/removeFavorite")
    public ResponseEntity<String> removeFavorite(@RequestParam Integer userId, @RequestParam Integer restaurantId) {
        favoriteRestaurantService.removeFavorite(userId, restaurantId);
        return ResponseEntity.ok("餐廳已從收藏中移除");
    }
	 
	 // 查詢已收藏的餐廳
	 @GetMapping("/getFavoriteRestaurants")
	 public ResponseEntity<List<Restaurants>> getFavoriteRestaurants(@RequestParam Integer userId) {
		 
	     List<Restaurants> favoriteRestaurants = favoriteRestaurantService.getFavoriteRestaurants(userId);
	     
	     return ResponseEntity.ok(favoriteRestaurants); 
	 }


}
