package com.richfood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.Coupons;
import com.richfood.repository.CouponsRepository;

@Service
public class CouponsService {
	@Autowired CouponsRepository couponsRepository;
	public List<Coupons> selectCoupons(Integer storeId) {
		return couponsRepository.findByStoreId(storeId);
	}
}
