package com.richfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richfood.model.Reservations;

@Repository
public interface ReservationStoreRepository extends JpaRepository<Reservations, Integer>{
	@Query(value="SELECT * FROM public.reservations\r\n"
			+ "WHERE store_id=?1 AND state !=false\r\n"
			+ "AND reservation_date >= CURRENT_DATE " 
			+ "ORDER BY reservation_date ASC,reservation_time ASC, reservation_id ASC", nativeQuery = true)
	List<Reservations> findByStoreIdReservationNotCancelAsc(Integer storeid);
}
