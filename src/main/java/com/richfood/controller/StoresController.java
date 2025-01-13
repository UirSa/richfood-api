package com.richfood.controller;



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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Store;
import com.richfood.service.StoresService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



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
	public ResponseEntity<Store> storeLogin(@RequestBody Store store, HttpServletRequest request) {
		boolean isStore =storesService.storeLogin(store.getStoreAccount(), store.getPassword());
		if(isStore) {
	        Store storeData = storesService.getStoreDatail(store.getStoreAccount());
	        
	        // 將資料存入 session
	        HttpSession session = request.getSession();
	        session.setAttribute("storeId", storeData.getStoreId());
	        session.setAttribute("restaurantId", storeData.getRestaurantId());
	        session.setAttribute("storeName", storeData.getStoreAccount());
	        return ResponseEntity.ok(storeData);

		}else {
	       
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
		
		
	}
	@GetMapping("/storeLogOut")
	public ResponseEntity<String> storeLogout(HttpServletRequest request) {
		
		 HttpSession session = request.getSession(false); 
		    if (session != null) {
		        session.invalidate();
		        System.out.println("Session invalidated");
		    } else {
		        System.out.println("No session found to invalidate");
		    }
		    storesService.storeLogout();

		return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
	}
	@GetMapping("/getStore")
	public ResponseEntity<Store> getStoreDatail(@RequestParam Integer storeId) {
		Store store=storesService.getStoreDatail(storeId);
		return ResponseEntity.ok(store);
	}
	@PutMapping("/updateStore")
	public ResponseEntity<Store> updateStore(HttpServletRequest request,@RequestBody Store store) {
		Integer storeId=(Integer)request.getSession().getAttribute("storeId");
		;
		
		Store updateStore=
		storesService.updateStore(storeId,store.getPassword());
		return ResponseEntity.ok(updateStore);
	}
	@DeleteMapping("/deleteStore/{storeId}")
	public ResponseEntity<String> deleteStore(@PathVariable Integer storeId){
		
//		System.out.println(storeId);
		storesService.deleteStore(storeId);
		return ResponseEntity.status(HttpStatus.OK).body("delete successful");
	}
}
