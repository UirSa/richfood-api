package com.richfood.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.richfood.model.Reservations;
import com.richfood.repository.ReservationRepository;
import com.richfood.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/reservation")
public class ReservationController {
	@Autowired
    private ReservationService reservationService;

	//增加使用者訂位
	@PostMapping("/addseat")
	public ResponseEntity<Reservations> addSeat(@RequestBody Reservations reservations, HttpServletRequest request) {

		   Integer userId = (Integer) request.getSession().getAttribute("userId");

		    if (userId == null) {
		        Map<String, Object> errorResponse = new HashMap<>();
		        errorResponse.put("message", "未登入");
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // 如果沒登入，返回401
		    }

		    reservations.setUserId(userId); 
		

		Reservations reservation=reservationService.addSeat(reservations);
		return ResponseEntity.ok(reservation);
	}
	
	//刪除使用者訂位//資料庫不留
	@DeleteMapping("/deleteSeat/{reservationId}")
	public void deleteSeat(@PathVariable Integer reservationId) {
		reservationService.deleteSeat(reservationId);
		
	}
	
	//修改訂位//刪除用這裡更改狀態
	@PutMapping("/updateSeat/{reservationId}")
	public ResponseEntity<Reservations> updateSeat(@PathVariable Integer reservationId,@RequestBody Reservations updatedReservation) {
		Reservations reservation = reservationService.updateSeat(reservationId, updatedReservation);
		return ResponseEntity.ok(reservation);
	}
	
	//消費者查詢所有訂位 排序依訂位到期時日期是舊到新 時間是舊到新 歷史訂單 不排除各種條件
	@GetMapping("/selectAllReservationAsc")
	public ResponseEntity<List<Reservations>>selectAllAscReservation(HttpServletRequest request) {
		
		Integer userId= (Integer)request.getSession().getAttribute("userId");
		//System.out.println(userId);
		List<Reservations> reservations=reservationService.seleteSeatAsc(userId);
		
		return ResponseEntity.ok(reservations);
	}
	//消費者查詢所有訂位 預設排序依訂位到期日期是新到舊 時間是舊到新 歷史訂單 不排除各種條件
	@GetMapping("/selectAllReservationDesc")
	public ResponseEntity<List<Reservations>>selectAllReservation(HttpServletRequest request) {
		
		Integer userId= (Integer)request.getSession().getAttribute("userId");
		List<Reservations> reservations=reservationService.seleteSeatDesc(userId);
		
		return ResponseEntity.ok(reservations);
	}
	//消費者查詢未來訂位含今日 預設排序依訂位到期日期是舊到新 時間是舊到新  排除已取消訂單status!=false
	@GetMapping("/selectReservationNotCancelAsc")
	public ResponseEntity<List<Reservations>>selectReservationNotCancelAsc(HttpServletRequest request){
		
		Integer userId= (Integer)request.getSession().getAttribute("userId");
		List<Reservations> reservations=reservationService.seleteSeatNotCancelAsc(userId);
		
		return ResponseEntity.ok(reservations);
	}
	//消費者查詢未來訂位含今日 預設排序依訂位到期日期是新到舊 時間是舊到新  排除已取消訂單status!=false
	@GetMapping("/selectReservationNotCancelDesc")
	public ResponseEntity<List<Reservations>>selectReservationNotCancelDesc(HttpServletRequest request){
		
		Integer userId= (Integer)request.getSession().getAttribute("userId");
		List<Reservations> reservations=reservationService.seleteSeatNotCancelDesc(userId);
		
		return ResponseEntity.ok(reservations);
	}
	
	
	
	
}
