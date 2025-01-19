package com.richfood.repository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.richfood.model.Reservations;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
	List<Reservations> findByUserId(Integer userid);
	boolean existsByUserIdAndStoreIdAndStateTrueAndReservationDateAndReservationTime(Integer userId,Integer storeId, String date, String reservationTime);
	
	@Query(value = "SELECT * FROM public.reservations " +
            "WHERE user_id = ?1 " +
            "ORDER BY " +
            "reservation_date ASC, " +
            "CASE reservation_time " +
            "    WHEN '早上' THEN 1 " +
            "    WHEN '中午' THEN 2 " +
            "    WHEN '晚上' THEN 3 " +
            "    ELSE 4 " +
            "END, " +
            "store_id ASC", nativeQuery = true)
	List<Reservations> findByUserIdOrderByAsc(Integer userid);
	
	
	@Query(value = "SELECT * FROM public.reservations " +
            "WHERE user_id = ?1 " +
            "ORDER BY " +
            "reservation_date DESC, " +
            "CASE reservation_time " +
            "    WHEN '早上' THEN 1 " +
            "    WHEN '中午' THEN 2 " +
            "    WHEN '晚上' THEN 3 " +
            "    ELSE 4 " +
            "END, " +
            "store_id ASC", nativeQuery = true)
	List<Reservations> findByUserIdOrderByDesc(Integer userid);
	
	
	@Query(value = "SELECT * FROM public.reservations " +
	        "WHERE user_id=?1 AND state != false " +
	        "AND TO_DATE(reservation_date, 'YYYY-MM-DD') >= CURRENT_DATE " + 
	        "ORDER BY " +
	        "reservation_date ASC, " +
	        "CASE reservation_time " +
	        "    WHEN '早上' THEN 1 " +
	        "    WHEN '中午' THEN 2 " +
	        "    WHEN '晚上' THEN 3 " +
	        "    ELSE 4 " +
	        "END, " +
	        "store_id ASC", nativeQuery = true)
	List<Reservations> findByUserIdReservationNotCancelAsc(Integer userid);
	
	@Query(value = "SELECT * FROM public.reservations " +
	        "WHERE user_id=?1 AND state != false " +
	        "AND TO_DATE(reservation_date, 'YYYY-MM-DD') >= CURRENT_DATE " +  
	        "ORDER BY " +
	        "reservation_date DESC, " +
	        "CASE reservation_time " +
	        "    WHEN '早上' THEN 1 " +
	        "    WHEN '中午' THEN 2 " +
	        "    WHEN '晚上' THEN 3 " +
	        "    ELSE 4 " +
	        "END, " +
	        "store_id ASC", nativeQuery = true)
	List<Reservations> findByUserIdReservationNotCancelDesc(Integer userid);
	
	@Query(value ="DELETE FROM public.Reservations r WHERE r.store_id IS NULL", nativeQuery = true)
	void deleteOrphanReservations();
}
