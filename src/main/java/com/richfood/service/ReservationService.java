package com.richfood.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        // 自動設置 editTime 為當前時間
        reservation.setEditTime(OffsetDateTime.now());
        
        boolean exists = reservationRepository.existsByUserIdAndStoreIdAndStateTrueAndReservationDateAndReservationTime(
                reservation.getUserId(), 
                reservation.getStoreId(), 
                reservation.getReservationDate(),
                reservation.getReservationTime() 
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
             
             if(existingReservation.getReservationDate()!= null) {
            	 OffsetDateTime now = OffsetDateTime.now();
            	 LocalDate previousDay = now.toLocalDate().minusDays(1);
            	 String reservationDate= existingReservation.getReservationDate();
//            	 LocalDate reservationLocalDate = reservationDate.toInstant()
//                         .atZone(ZoneId.systemDefault())
//                         .toLocalDate();
            	 LocalDate reservationLocalDate = LocalDate.parse(reservationDate);

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
             return reservationRepository.save(existingReservation);
         }

         // 若不存在，返回
         return null;
     }
    //查 
    //消費者查詢所有訂位 預設排序舊到新
    public List<Reservations>seleteSeatAsc(Integer userid) {
    	 List<Reservations> userReservations=reservationRepository.findByUserIdOrderByAsc(userid);
    	 return userReservations;
    }
    public List<Reservations>seleteSeatDesc(Integer userid) {
   	 	List<Reservations> userReservations=reservationRepository.findByUserIdOrderByDesc(userid);
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
    
    public Reservations getReservationById(Integer reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }
	

	
}
