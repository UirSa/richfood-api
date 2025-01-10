package com.richfood.service;

import java.util.List;
import java.util.stream.Collectors;

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
		//關聯開啟前不要開 下面是取得名字還差電話之類的資訊  做完這邊回去補客戶那邊取得餐廳名字
		//List<String>test=storeReservations.stream().map(reservation -> reservation.getUsers().getName()).collect(Collectors.toList());
		//System.out.println(test);
		return storeReservations;
		
	}
	
}
