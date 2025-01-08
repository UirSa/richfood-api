package com.richfood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.FavoriteRestaurants;
import com.richfood.model.Restaurants;
import com.richfood.model.Users;
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
	    public FavoriteRestaurants addFavoriteRestaurant(Integer userId, Integer restaurantId) {
	        Optional<Users> userOptional = userRepository.findById(userId);
	        Optional<Restaurants> restaurantOptional = restaurantRepository.findById(restaurantId);

	        if (!userOptional.isPresent()) {
	            throw new IllegalArgumentException("User not found"+ userId);
	        }
	        if (!restaurantOptional.isPresent()) {
	            throw new IllegalArgumentException("Restaurant not found"+ restaurantId);
	        }

	        // 新增收藏
	        FavoriteRestaurants favoriteRestaurant = new FavoriteRestaurants();
	        favoriteRestaurant.setUserId(userId);
	        favoriteRestaurant.setRestaurantId(restaurantId);

	        return favoriteRestaurantRepository.save(favoriteRestaurant);
	    }
	 @Transactional
	 public void CancelFavorite(Integer userId, Integer restaurantId) {
		 Optional<FavoriteRestaurants> existingFavorite=favoriteRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId);
		 
		 if (existingFavorite.isPresent()) {
	            // 如果已經收藏，則取消收藏（刪除該條收藏記錄）
	            favoriteRestaurantRepository.delete(existingFavorite.get());
	        } else {
	            // 如果沒有收藏，則新增收藏
	            FavoriteRestaurants favoriteRestaurant = new FavoriteRestaurants();
	            favoriteRestaurant.setUserId(userId);
	            favoriteRestaurant.setRestaurantId(restaurantId);
	            favoriteRestaurantRepository.save(favoriteRestaurant);
	        }
	 }
	 
	 
	
}
