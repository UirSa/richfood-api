package com.richfood.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
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
	@PostMapping("/addStoreCapacity")
	public ResponseEntity<RestaurantCapacity> addCapacity(@RequestBody RestaurantCapacity restaurantCapacity){

		restaurantCapacityService.addCapacity(restaurantCapacity);
		return ResponseEntity.ok(restaurantCapacity);
	}
	@GetMapping("/getMaxCapacity")
	public ResponseEntity<List<RestaurantCapacity>> getCapacity(@RequestParam Integer storeId){

		return ResponseEntity.ok(restaurantCapacityService.getCapacity(storeId));
	}
	@GetMapping("/getCapacityList")
	public ResponseEntity<List<RestaurantCapacity>> getCapacityList(HttpServletRequest request,
	        @RequestParam(value = "storeId", required = false) Integer storeId){
	    if (storeId == null) {
	        storeId = (Integer) request.getSession().getAttribute("storeId");
	    }
		return ResponseEntity.ok(restaurantCapacityService.getCapacity(storeId));
	}
	@GetMapping("/getStoreIdCapacityList")
	public ResponseEntity<List<RestaurantCapacity>> getStoreIdCapacityList(Integer storeId){
		return ResponseEntity.ok(restaurantCapacityService.getCapacity(storeId));
	}
	@PostMapping("/searchSameTime")
    public boolean searchSameTime(@RequestBody RestaurantCapacity request) {
    	boolean record =restaurantCapacityService.searchSameTime(
    			request.getStoreId(),
    	        request.getDate(),
    	        request.getTime());
    	return record;
    }
	
	@PutMapping("/searchAfterUpdate")
	public RestaurantCapacity searchAfterUpdate(@RequestBody RestaurantCapacity request) {
	    return restaurantCapacityService.searchAfterUpdate(
	        request.getStoreId(),
	        request.getDate(),
	        request.getTime(),
	        request.getMaxCapacity()
	    );
	}
	

	
	
	
	
	
}
