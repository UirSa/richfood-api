package com.richfood.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.Reservations;
import com.richfood.repository.ReservationStoreRepository;


@Service
public class ReservationStoreService {
	@Autowired
	private ReservationStoreRepository reservationStoreRepository;
	
	
	//增加可訂位數
	
	
	//查詢已訂位資料
	public List<Reservations>seleteSeatNotCancelAsc(Integer storeId){
		List<Reservations> storeReservations=reservationStoreRepository.findByStoreIdReservationNotCancelAsc(storeId);
	
		return storeReservations;
	}
	//修改狀態
	  public Reservations updateSeat(Integer reservationId,Reservations updatedReservation) {
	    	Optional<Reservations> optionalorder=reservationStoreRepository.findById(reservationId);
	    	
	         if (optionalorder.isPresent()) {
	             Reservations existingReservation = optionalorder.get();
	             
	             if(existingReservation.getReservationDate()!= null) {
	            	 OffsetDateTime now = OffsetDateTime.now();
	            	 LocalDate previousDay = now.toLocalDate().minusDays(1);
	            	 Date reservationDate= existingReservation.getReservationDate();
	            	 LocalDate reservationLocalDate = reservationDate.toInstant()
	                         .atZone(ZoneId.systemDefault())
	                         .toLocalDate();

	                 if (reservationLocalDate.equals(now.toLocalDate())||reservationLocalDate.equals(previousDay)) {
	                     throw new IllegalStateException("不能更改當天及前一天的預約。");
	                     
	                 }
	                 // 更新資料
	                 if (updatedReservation.getNumPeople() != null) {
	                	 existingReservation.setNumPeople(updatedReservation.getNumPeople());
	                 }
	                 if (updatedReservation.getReservationDate() != null) {
	                	 existingReservation.setReservationDate(updatedReservation.getReservationDate());
	                 }
	                 if (updatedReservation.getReservationTime() != null) {
	                	 existingReservation.setReservationTime(updatedReservation.getReservationTime());
	                 }
	                 if(updatedReservation.getState()!=null) {
	                	 existingReservation.setState(updatedReservation.getState());
	                 }

	             }

	             
	             // 保存更新的資料
	             return reservationStoreRepository.save(existingReservation);
	         }

	         // 若不存在，返回
	         return null;
	     }
	
}
