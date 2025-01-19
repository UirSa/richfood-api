package com.richfood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Reservations;
import com.richfood.service.ReservationStoreService;
import com.richfood.service.RestaurantCapacityService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/storeReservation")
public class ReservationStoreController {
	@Autowired
	private ReservationStoreService reservationStoreService;
	@Autowired 
	private RestaurantCapacityService restaurantCapacityService;
	
	//刪除預定(更改狀態)
	@PutMapping("/updateSeat/{reservationId}")
	public ResponseEntity<Reservations> updateSeat(@PathVariable Integer reservationId,@RequestBody Reservations updatedReservation) {
		// 根據 reservationId 取得現有訂單
			Reservations existingReservation = reservationStoreService.getReservationById(reservationId);
			if (existingReservation == null) {
				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("message", "找不到該筆訂單");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 返回404
				    }
				// 取得現有訂單資訊
				Integer storeId=existingReservation.getStoreId();
				String reservationDate=existingReservation.getReservationDate();
				String reservationTime=existingReservation.getReservationTime();
				Integer justmentNum = existingReservation.getNumPeople();
				    
				//增加座位數量
				restaurantCapacityService.updateMaxCapacity(storeId,reservationDate,reservationTime,justmentNum);
		
		Reservations reservation = reservationStoreService.updateSeat(reservationId, updatedReservation);
		return ResponseEntity.ok(reservation);
	}

	//瀏覽頁面
	@GetMapping("/selectAllReservationAsc")
	public ResponseEntity<List<Reservations>>selectAllReservation(HttpServletRequest request){
		Integer storeid= (Integer)request.getSession().getAttribute("storeId");
		List<Reservations> reservations=reservationStoreService.seleteSeatNotCancelAsc(storeid);
		
		return ResponseEntity.ok(reservations);
	}
	
}
