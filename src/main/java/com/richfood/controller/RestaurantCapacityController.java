package com.richfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.RestaurantCapacity;

import com.richfood.service.RestaurantCapacityService;

@RestController
@RequestMapping("/restaurantCapacity")
public class RestaurantCapacityController {
	@Autowired RestaurantCapacityService restaurantCapacityService;
	
	//查詢
//	@GetMapping("/getRestaurantCapacity")
//	public ResponseEntity<RestaurantCapacity> getDatail(@RequestParam Integer storeId) {
//		RestaurantCapacity restaurantCapacity=restaurantCapacityService.getDatail(storeId);
//		return ResponseEntity.ok(restaurantCapacity);
//	}
	@GetMapping("/test")
	public void updateMaxCapacity() {
		Integer numPeople=200;
		Integer justmentNum=-numPeople;
		System.out.println(-numPeople);
		restaurantCapacityService.updateMaxCapacity(1,"2025-12-01","早上",justmentNum);
	}
	
	//新增
	
	//修改狀態
	
	
}
