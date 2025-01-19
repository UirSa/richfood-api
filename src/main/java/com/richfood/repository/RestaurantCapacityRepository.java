package com.richfood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richfood.model.RestaurantCapacity;


@Repository
public interface RestaurantCapacityRepository extends JpaRepository<RestaurantCapacity, Integer>{
	Optional<RestaurantCapacity> findByStoreId(Integer storeId);
	
//	@Query(value="SELECT * FROM public.restaurant_capacity\r\n"
//			+ "WHERE store_id=1  AND time= '早上時段'AND date='2025-12-01'", nativeQuery = true)
	RestaurantCapacity findLastByStoreIdAndDateAndTime(Integer storeid,String date,String time);


}
