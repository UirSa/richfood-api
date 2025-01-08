package com.richfood.repository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richfood.model.Reservations;


@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
//	List<Reservations> findByUserId(String userid);
	boolean existsByUserIdAndReservationDateAndReservationTimeAndStoreId(Integer userId, Date date, Time reservationTime, Integer storeId);
}
