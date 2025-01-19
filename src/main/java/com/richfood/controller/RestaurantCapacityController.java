package com.richfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.RestaurantCapacity;

import com.richfood.service.RestaurantCapacityService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/restaurantCapacity")
public class RestaurantCapacityController {
	@Autowired RestaurantCapacityService restaurantCapacityService;
	

	//新增
	@PostMapping("/addCapacity")
	public ResponseEntity<RestaurantCapacity> addCapacity(@RequestBody RestaurantCapacity restaurantCapacity, HttpServletRequest request){
		Integer storeId = (Integer) request.getSession().getAttribute("storeId");
		restaurantCapacity.setStoreId(storeId);
		
		restaurantCapacityService.addCapacity(restaurantCapacity);
		return ResponseEntity.ok(restaurantCapacity);
	}
	
	
}
