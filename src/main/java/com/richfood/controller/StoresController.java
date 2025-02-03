package com.richfood.controller;



import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.richfood.model.Store;
import com.richfood.service.StoresService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/store")
@CrossOrigin(origins = "http://localhost:5173")
public class StoresController {
	@Autowired StoresService storesService;
	
	@PostMapping("/add")
	public ResponseEntity<Store> storeAdd(@RequestBody Store store) {
		Store newStore= storesService.addStore(store);
		return new ResponseEntity<>(newStore,HttpStatus.CREATED);
	}
	
	@PostMapping("/storeLogin")
	public ResponseEntity<?> storeLogin(@RequestBody Store store, HttpServletRequest request) {
		boolean isStoreExist =storesService.storeLogin(store.getStoreAccount(), store.getPassword());
		if(isStoreExist) {
	        Store storeData = storesService.getStoreDatail(store.getStoreAccount());
	        
	        // 將資料存入 session
	        HttpSession session = request.getSession();
	        session.setAttribute("storeId", storeData.getStoreId());
	        session.setAttribute("restaurantId", storeData.getRestaurantId());
	        session.setAttribute("storeName", storeData.getStoreAccount());
	        session.setAttribute("userType", "store");
	        return ResponseEntity.ok(storeData);

		}else {
	       // 驗證失敗
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", "Bad Request",
                        "message", "帳號或密碼錯誤",
                        "status", HttpStatus.BAD_REQUEST.value()
                    ));
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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
	@GetMapping("/selectStore")
	public ResponseEntity<Store> selectStore(HttpServletRequest request) {
		Integer storeId=(Integer)request.getSession().getAttribute("storeId");
		Store store=storesService.getStoreDatail(storeId);
		return ResponseEntity.ok(store);
	}
	
	@PostMapping("/updateStore")
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
	
	 @PutMapping("/icon/{storeId}")
	    public Store updateIcon(HttpServletRequest request, @RequestBody String base64Icon) {
		 	Integer storeId=(Integer)request.getSession().getAttribute("storeId");
	        return storesService.updateUserIcon(storeId, base64Icon);
	    }
	
	
}
