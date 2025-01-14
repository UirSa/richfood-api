package com.richfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.CouponsOrders;
import com.richfood.repository.CouponsOrdersRepository;


@Service
public class CouponsOrdersService {
	@Autowired CouponsOrdersRepository couponsOrdersRepository;
	
	public CouponsOrders addCouponsOrder(CouponsOrders couponsOrders) {
		System.out.println(couponsOrders);
	
		return couponsOrdersRepository.save(couponsOrders);
		
	}
	
	
}
