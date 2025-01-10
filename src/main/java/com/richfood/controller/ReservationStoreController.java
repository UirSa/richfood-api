package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Reservations;
import com.richfood.service.ReservationStoreService;

@RestController
@RequestMapping("/storeReservation")
public class ReservationStoreController {
	@Autowired
	private ReservationStoreService reservationStoreService;
	
	//刪除預定(更改狀態)
	
	//瀏覽頁面

	@GetMapping("/selectAllReservationAsc/{storeid}")
	public ResponseEntity<List<Reservations>>selectAllReservation(@PathVariable Integer storeid){
		List<Reservations> reservations=reservationStoreService.seleteSeatNotCancelAsc(storeid);
		
		return ResponseEntity.ok(reservations);
	}
	
}
