package com.richfood.repository;

import com.richfood.model.Store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
	Optional<Store> findByStoreAccount(String storeAccount);
	
	
}
