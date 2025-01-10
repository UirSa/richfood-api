package com.richfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Store;
import com.richfood.service.StoresService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/store")
public class StoresController {
	@Autowired StoresService storesService;
	
	@PostMapping("/add")
	public ResponseEntity<Store> storeAdd(@RequestBody Store store) {
		Store newStore= storesService.addStore(store);
		return new ResponseEntity<>(newStore,HttpStatus.CREATED);
	}
	
	@PostMapping("/storeLogin")
	public void storeLogin(@RequestBody Store store, HttpServletRequest request) {
		boolean isStore =storesService.storeLogin(store.getStoreAccount(), store.getPassword());
		if(isStore) {
			request
		}
		
	}
	public void storeLogout() {
		
	}
	public void getStoreDatail() {
		
	}
	public void updateStore() {
		
	}
}
