package com.richfood.repository;

import com.richfood.model.Coupons;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons,Integer> {
	List<Coupons> findByStoreId(Integer storeId);
}
