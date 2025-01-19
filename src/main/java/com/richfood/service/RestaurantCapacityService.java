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
	
//	public RestaurantCapacity getDatail(Integer storeId) {
//		Optional<RestaurantCapacity> optionalStore=restaurantCapacityRepository.findByStoreId(storeId);
//		RestaurantCapacity restaurantCapacity=optionalStore.get();
//		return restaurantCapacity;
//	}
	
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
}
