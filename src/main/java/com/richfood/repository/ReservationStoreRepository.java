package com.richfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richfood.model.Reservations;

@Repository
public interface ReservationStoreRepository extends JpaRepository<Reservations, Integer>{

	@Query(value = "SELECT * FROM public.reservations " +
	        "WHERE store_id=?1 AND state != false " +
	        "AND TO_DATE(reservation_date, 'YYYY-MM-DD') >= CURRENT_DATE " + 
	        "ORDER BY " +
	        "reservation_date ASC, " +
	        "CASE reservation_time " +
	        "    WHEN '早上' THEN 1 " +
	        "    WHEN '中午' THEN 2 " +
	        "    WHEN '晚上' THEN 3 " +
	        "    ELSE 4 " +
	        "END " , nativeQuery = true)
	List<Reservations> findByStoreIdReservationNotCancelAsc(Integer storeid);
}
