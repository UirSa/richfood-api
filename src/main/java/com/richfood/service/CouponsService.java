package com.richfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.repository.CouponsRepository;

@Service
public class CouponsService {
	@Autowired CouponsRepository couponsRepository;

}
