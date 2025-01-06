package com.richfood.repository;

import com.richfood.model.CouponsOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsOrdersRepository extends JpaRepository<CouponsOrders, Integer> {
}
