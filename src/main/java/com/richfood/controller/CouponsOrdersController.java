package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.CouponsOrders;
import com.richfood.service.CouponsOrdersService;
import com.richfood.service.LinePayService;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/couponsOrder")
public class CouponsOrdersController {
	@Autowired CouponsOrdersService couponsOrdersService;
	@Autowired LinePayService linePayService;
	
	//輸入couponid、quantity、price、storeId購買餐券
	@PostMapping("/addCouponsOrder")
	public String addCouponsOrder(@RequestBody CouponsOrders couponsOrders, HttpServletRequest request) {
		
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		couponsOrders.setUserId(userId);
		couponsOrders.setTotalPrice(couponsOrders.getPrice()*couponsOrders.getQuantity());
		couponsOrders.setStatus(false);
		//訂單存入資料庫
		CouponsOrders couponsOrder=couponsOrdersService.addCouponsOrder(couponsOrders);
		String url=linePayService.requestPaymentImmediate(userId);
		 
		return url;
	}
	//查出所有餐券//不含status==false
	@GetMapping("/selectCouponsOrder")
	public List<CouponsOrders> selectCouponsOrder(HttpServletRequest request) {
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		List<CouponsOrders> couponsOrders=couponsOrdersService.selectCouponsOrder(userId);
		return couponsOrders;
	}
	@GetMapping("/selectAllCouponsOrder")
	public List<CouponsOrders> selectAllCouponsOrder(HttpServletRequest request){
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		List<CouponsOrders> couponsOrders=couponsOrdersService.selectAllCouponsOrder(userId);
		return couponsOrders;
	}
	@GetMapping("/selectAllStoreCouponsOrder")
	public List<CouponsOrders> selectAllStoreCouponsOrder(HttpServletRequest request){
		Integer storeId = (Integer) request.getSession().getAttribute("storeId");
		List<CouponsOrders> couponsOrders=couponsOrdersService.selectAllStoreCouponsOrder(storeId);
		return couponsOrders;
	}
	
	
	//更改狀態
	@GetMapping("/usedCoupon")
	public CouponsOrders usedCoupon(@RequestParam Integer orderId) {
		return couponsOrdersService.usedCoupon(orderId);
		
	}
	
	
	
	
}
