package com.richfood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.RestaurantCapacity;

import com.richfood.repository.RestaurantCapacityRepository;

import jakarta.transaction.Transactional;

@Service
public class RestaurantCapacityService {
	@Autowired RestaurantCapacityRepository restaurantCapacityRepository;
	

	
    public void updateMaxCapacity(int storeid,String date,String time,int adjustment) {
        // 找到目標資料
        RestaurantCapacity capacity =restaurantCapacityRepository.findLastByStoreIdAndDateAndTime(storeid,date,time);
       
        // 計算新的 maxCapacity
        int newCapacity = capacity.getMaxCapacity() + adjustment;

        // 若新容量 < 0，拋出例外
        if (newCapacity < 0) {
            throw new IllegalArgumentException("座位不足");
        }

        // 更新 maxCapacity
        capacity.setMaxCapacity(newCapacity);
        
        restaurantCapacityRepository.save(capacity);

    }
    
    public RestaurantCapacity addCapacity(RestaurantCapacity restaurantCapacity) {
    	return restaurantCapacityRepository.save(restaurantCapacity);

    }
    public List<RestaurantCapacity> getCapacity(Integer storeId) {
    	
    	
    	return restaurantCapacityRepository.findByStoreIdOrderByDateAndTime(storeId);
    	
    	
    }
    
    public boolean searchSameTime(Integer storeId,String date,String time) {
    	Object record =restaurantCapacityRepository.findLastByStoreIdAndDateAndTime(storeId,date,time);
    	if(record !=null) {
    		return true;
    	}
    	return false;
    }
    
    
    public RestaurantCapacity searchAfterUpdate(Integer storeId,String date,String time,Integer maxCapacity) {
    	RestaurantCapacity restaurantCapacity=restaurantCapacityRepository.findLastByStoreIdAndDateAndTime(storeId,date,time);
    	restaurantCapacity.setMaxCapacity(maxCapacity);
    	return restaurantCapacityRepository.save(restaurantCapacity);
    	
    }
}
