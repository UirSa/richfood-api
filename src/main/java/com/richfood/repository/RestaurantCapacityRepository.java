package com.richfood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richfood.model.RestaurantCapacity;


@Repository
public interface RestaurantCapacityRepository extends JpaRepository<RestaurantCapacity, Integer>{
	List<RestaurantCapacity> findByStoreId(Integer storeId);
	
//	@Query(value="SELECT * FROM public.restaurant_capacity\r\n"
//			+ "WHERE store_id=1  AND time= '早上時段'AND date='2025-12-01'", nativeQuery = true)
	@Query(value = "SELECT * FROM public.restaurant_capacity " +
	        "WHERE store_id=?1 " +
	        "AND TO_DATE(date, 'YYYY-MM-DD') >= CURRENT_DATE " + 
	        "ORDER BY " +
	        "date ASC, " +
	        "CASE time " +
	        "    WHEN '早上' THEN 1 " +
	        "    WHEN '中午' THEN 2 " +
	        "    WHEN '晚上' THEN 3 " +
	        "    ELSE 4 " +
	        "END " , nativeQuery = true)
	List<RestaurantCapacity> findByStoreIdOrderByDateAndTime(Integer storeId);
	
	
	RestaurantCapacity findLastByStoreIdAndDateAndTime(Integer storeid,String date,String time);
	

}
