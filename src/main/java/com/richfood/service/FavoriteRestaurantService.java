package com.richfood.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.richfood.dto.BusinessHoursDto;
import com.richfood.model.BusinessHours;
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

	 public List<Map<String, Object>> getFavoriteRestaurantsWithDetails(Integer userId) {
		    System.out.println("接收到的 userId: " + userId); // 檢查 userId

		    List<FavoriteRestaurants> favorites = favoriteRestaurantRepository.findByUserIdOrderByFavoriteIdDesc(userId);
		    System.out.println("查詢到的收藏記錄: " + favorites); // 檢查收藏記錄

		    List<Map<String, Object>> result = new ArrayList<>();
		    for (FavoriteRestaurants favorite : favorites) {
		        Restaurants restaurant = restaurantRepository.findById(favorite.getRestaurantId())
		                .orElse(null);

		        System.out.println("查詢餐廳 (restaurantId=" + favorite.getRestaurantId() + "): " + restaurant); // 檢查餐廳記錄

		        if (restaurant != null) {
		            Map<String, Object> restaurantData = new HashMap<>();
		            restaurantData.put("restaurantId", restaurant.getRestaurantId());
		            restaurantData.put("name", restaurant.getName());
		            restaurantData.put("address", restaurant.getAddress());
		            restaurantData.put("image", restaurant.getImage()); // 添加 image 字段


		            // 組裝營業時間
		            List<Map<String, String>> businessHours = new ArrayList<>();
		            for (BusinessHours hours : restaurant.getBusinessHours()) {
		                Map<String, String> hoursData = new HashMap<>();
		                hoursData.put("dayOfWeek", hours.getBusinessHoursId().getDayOfWeek());
		                hoursData.put("startTime", hours.getBusinessHoursId().getStartTime());
		                hoursData.put("endTime", hours.getEndTime());
		                businessHours.add(hoursData);
		            }
		            restaurantData.put("businessHours", businessHours);

		            result.add(restaurantData);
		        }
		    }
		    System.out.println("最終返回的數據: " + result); // 檢查最終結果
		    return result;
		}
	
}
