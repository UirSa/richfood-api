package com.richfood.repository;

import com.richfood.model.CouponsOrders;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsOrdersRepository extends JpaRepository<CouponsOrders, Integer> {
	Optional<CouponsOrders> findTopByUserIdAndStatusIsFalseOrderByOrderIdDesc(Integer userId);
	List<CouponsOrders> findByUserIdAndStatusIsTrue(Integer userId);
}
