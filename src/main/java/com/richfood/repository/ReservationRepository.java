package com.richfood.repository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richfood.model.Reservations;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
	List<Reservations> findByUserId(Integer userid);
	boolean existsByUserIdAndStoreIdAndStateTrueAndReservationDateAndReservationTime(Integer userId,Integer storeId,  Date date, Time reservationTime);
	
	List<Reservations> findByUserIdOrderByReservationDateAscReservationTimeAsc(Integer userid);
	List<Reservations> findByUserIdOrderByReservationDateDescReservationTimeAsc(Integer userid);
	
	@Query(value="SELECT * FROM public.reservations\r\n"
			+ "WHERE user_id=?1 AND state !=false\r\n"
			+ "AND reservation_date >= CURRENT_DATE " 
			+ "ORDER BY reservation_date ASC,reservation_time ASC", nativeQuery = true)
	List<Reservations> findByUserIdReservationNotCancelAsc(Integer userid);
	@Query(value="SELECT * FROM public.reservations\r\n"
			+ "WHERE user_id=?1 AND state !=false\r\n"
			+ "AND reservation_date >= CURRENT_DATE \r\n"
			+ "ORDER BY reservation_date Desc,reservation_time ASC", nativeQuery = true)
	List<Reservations> findByUserIdReservationNotCancelDesc(Integer userid);
}
