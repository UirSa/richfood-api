package com.richfood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.Store;
import com.richfood.repository.CouponsOrdersRepository;
import com.richfood.repository.CouponsRepository;
import com.richfood.repository.ReservationRepository;
import com.richfood.repository.StoreRepository;
import com.richfood.util.BCrypt;



@Service
public class StoresService {
	 @Autowired 
	 private StoreRepository storeRepository;
	 
	
	public Store addStore(Store store) {
		
		
		String storeAccount=store.getStoreAccount();
		if(storeRepository.findByStoreAccount(storeAccount).isPresent()) {
			 throw new IllegalArgumentException("This Account already exists");
		}
		store.setStoreAccount(storeAccount);
		store.setRestaurantId(store.getRestaurantId());
//		store.setPassword(store.getPassword());
		store.setPassword(BCrypt.hashpw(store.getPassword(), BCrypt.gensalt()));

		return storeRepository.save(store);

	}
	public boolean storeLogin(String storeAccount,String password) {
		Optional<Store> optionalStore=storeRepository.findByStoreAccount(storeAccount);
		if(optionalStore.isPresent()) {
			Store store=optionalStore.get();
			String abc=store.getPassword();
			return BCrypt.checkpw(password, store.getPassword());
//			System.out.println(abc.equals(password));
//			return abc.equals(password);
		}

		return false;
	}
	public void storeLogout() {

		System.out.println("logout");
		
	}
	public Store getStoreDatail(String storeAccount) {
		Optional<Store> optionalStore=storeRepository.findByStoreAccount(storeAccount);
		Store store=optionalStore.get();
		return store;
	}
	public Store getStoreDatail(Integer storeid) {
		Optional<Store> optionalStore=storeRepository.findByStoreId(storeid);
		Store store=optionalStore.get();
		return store;
	}
	
	public Store updateStore(Integer storeid,String password) {
		Optional<Store> optionalStore=storeRepository.findByStoreId(storeid);
		Store store=optionalStore.get();
//		store.setPassword(password);
//		System.out.println(password);
		store.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

		return storeRepository.save(store);
		
	}
	public void deleteStore(Integer storeId) {
		System.out.println(storeRepository.existsById(storeId));
		 if (!storeRepository.existsById(storeId)) {
		        throw new RuntimeException("Store ID " + storeId + " does not exist");
		 }
		 
		 Optional<Store> storeOptional = storeRepository.findById(storeId);
		 if (storeOptional.isPresent()) {
		     System.out.println(storeOptional.get().getStoreAccount());
		     storeRepository.deleteById(storeId);
		 } else {
		     throw new RuntimeException("Store ID " + storeId + " does not exist");
		 }
		
	}
}