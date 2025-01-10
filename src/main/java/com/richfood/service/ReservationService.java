package com.richfood.service;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.richfood.model.Reservations;
import com.richfood.repository.ReservationRepository;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
	
    public Reservations addSeat(Reservations reservation) {
        // 自動設置 editTime 為當前時間（+00）
        reservation.setEditTime(LocalDateTime.now());
        
        boolean exists = reservationRepository.existsByUserIdAndStoreIdAndStateTrueAndReservationDateAndReservationTime(
                reservation.getUserId(), 
                reservation.getStoreId(), 
                reservation.getReservationDate(),  // 使用 Date 類型
                reservation.getReservationTime() // 使用 Time 類型
            );
            
            if (exists) {
                // 若已存在，拋出異常
                throw new IllegalArgumentException("相同的預約已存在，無法新增");
            }

            // 若不存在相同預約，則保存新的預約
            reservation.setState(true);
            return reservationRepository.save(reservation);
    }
    
    public void deleteSeat(Integer reservationId) {
    	reservationRepository.deleteById(reservationId);
    }
    
    public Reservations updateSeat(Integer reservationId,Reservations updatedReservation) {
    	Optional<Reservations> optionalorder=reservationRepository.findById(reservationId);
    	
         if (optionalorder.isPresent()) {
             Reservations existingReservation = optionalorder.get();

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

             // 保存更新的資料
             return reservationRepository.save(existingReservation);
         }

         // 若不存在，返回
         return null;
     }
    //查 
    //消費者查詢所有訂位 預設排序舊到新
    public List<Reservations>seleteSeatAsc(Integer userid) {
    	 List<Reservations> userReservations=reservationRepository.findByUserIdOrderByReservationDateAscReservationTimeAsc(userid);
    	 return userReservations;
    }
    public List<Reservations>seleteSeatDesc(Integer userid) {
   	 	List<Reservations> userReservations=reservationRepository.findByUserIdOrderByReservationDateDescReservationTimeAsc(userid);
   	 	return userReservations;
    }
    public List<Reservations>seleteSeatNotCancelAsc(Integer userid){
    	List<Reservations> userReservations=reservationRepository.findByUserIdReservationNotCancelAsc(userid);
      	return userReservations;
    }
    public List<Reservations>seleteSeatNotCancelDesc(Integer userid){
    	List<Reservations> userReservations=reservationRepository.findByUserIdReservationNotCancelDesc(userid);
      	return userReservations;
    }
	

	
}
