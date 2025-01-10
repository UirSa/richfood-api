package com.richfood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richfood.model.Store;
import com.richfood.repository.StoreRepository;
import com.richfood.util.BCrypt;

@Service
public class StoresService {
	private @Autowired StoreRepository storeRepository;
	
	public Store addStore(Store store) {
		
		
		String storeAccount=store.getStoreAccount();
		if(storeRepository.findByStoreAccount(storeAccount).isPresent()) {
			 throw new IllegalArgumentException("This Account already exists");
		}
		store.setStoreAccount(storeAccount);
		store.setRestaurantId(store.getRestaurantId());
		store.setPassword(BCrypt.hashpw(store.getPassword(), BCrypt.gensalt()));

		return storeRepository.save(store);

	}
	public boolean storeLogin(String storeAccount,String password) {
		Optional<Store> optionalStore=storeRepository.findByStoreAccount(storeAccount);
		if(optionalStore.isPresent()) {
			Store store=optionalStore.get();
			return BCrypt.checkpw(password, store.getPassword());
		}

		return false;
	}
	public void storeLogout() {
		
	}
	public void getStoreDatail() {
		
	}
	public void updateStore() {
		
	}
}
