package com.richfood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
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
	
	public List<CouponsOrders> selectCouponsOrder(Integer userId) {
		
		List<CouponsOrders> couponsOrders=couponsOrdersRepository.findByUserIdAndStatusIsTrue(userId);
		System.out.println(couponsOrders.toString());
		return couponsOrders;
	}
	
	
}
