package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Coupons;
import com.richfood.service.CouponsService;

@RestController
@RequestMapping("/coupons")
public class CouponsController {
	@Autowired CouponsService couponsService;
	//查store的coupon
	@GetMapping("/selectCoupon")
	public ResponseEntity<List<Coupons>> selectCoupon(@RequestParam Integer storeId) {
		
		return ResponseEntity.ok(couponsService.selectCoupons(storeId));
	}
}
