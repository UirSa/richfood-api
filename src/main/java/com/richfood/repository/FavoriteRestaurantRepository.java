package com.richfood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.richfood.model.FavoriteRestaurants;


@Repository
public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurants, Integer> {
	
	 Optional<FavoriteRestaurants> findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
	 
	 List<FavoriteRestaurants> findByUserIdOrderByFavoriteIdDesc(Integer userId);  
	 
	 List<FavoriteRestaurants> findByRestaurantId(Integer restaurantId);
	 
	 boolean existsByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
	 

} 
