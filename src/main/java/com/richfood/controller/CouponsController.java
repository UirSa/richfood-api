package com.richfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.service.CouponsService;

@RestController
@RequestMapping("/coupons")
public class CouponsController {
	@Autowired CouponsService couponsService;
	//查store的coupon
}
