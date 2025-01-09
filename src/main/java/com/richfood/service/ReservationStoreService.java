package com.richfood.service;

import java.util.List;

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
	
}
