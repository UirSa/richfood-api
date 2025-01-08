package com.richfood.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.richfood.model.FavoriteRestaurants;
import com.richfood.model.Restaurants;

import com.richfood.repository.FavoriteRestaurantRepository;
import com.richfood.repository.RestaurantsRepository;
import com.richfood.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class FavoriteRestaurantService {

	@Autowired FavoriteRestaurantRepository favoriteRestaurantRepository;
	
	@Autowired UsersRepository userRepository;
	
	@Autowired RestaurantsRepository restaurantRepository;
	
	 
	 @Transactional
	    public void addFavorite(Integer userId, Integer restaurantId) {
	        // 檢查用戶是否已經收藏該餐廳
	        Optional<FavoriteRestaurants> existingFavorite = favoriteRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId);
	        
	        if (!existingFavorite.isPresent()) {
	            // 如果尚未收藏，則新增收藏
	            FavoriteRestaurants favoriteRestaurant = new FavoriteRestaurants();
	            favoriteRestaurant.setUserId(userId);
	            favoriteRestaurant.setRestaurantId(restaurantId);
	            favoriteRestaurantRepository.save(favoriteRestaurant);
	        }
	    }
	 
	 @Transactional
	    public void removeFavorite(Integer userId, Integer restaurantId) {
	        // 查找現有的收藏記錄
	        Optional<FavoriteRestaurants> existingFavorite = favoriteRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId);
	        
	        if (existingFavorite.isPresent()) {
	            // 如果已經收藏，則刪除該收藏
	            favoriteRestaurantRepository.delete(existingFavorite.get());
	        }
	    }
	 
	 
	 // 查詢已收藏的餐廳
	 public List<Restaurants> getFavoriteRestaurants(Integer userId) {
		    // 查詢並根據 favoriteId 排序，確保新的收藏排在最前面
		    List<FavoriteRestaurants> favoriteList = favoriteRestaurantRepository.findByUserIdOrderByFavoriteIdDesc(userId);
		    
		    List<Restaurants> restaurants = new ArrayList<>();
		    
		    for (FavoriteRestaurants favorite : favoriteList) {
		        Optional<Restaurants> restaurant = restaurantRepository.findById(favorite.getRestaurantId());
		        restaurant.ifPresent(restaurants::add);
		    }

		    return restaurants;
		}

	 
	
} 
