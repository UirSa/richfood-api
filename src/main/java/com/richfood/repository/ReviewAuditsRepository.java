package com.richfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richfood.model.ReviewAudits;

@Repository
public interface ReviewAuditsRepository extends JpaRepository<ReviewAudits, Integer> {
}

