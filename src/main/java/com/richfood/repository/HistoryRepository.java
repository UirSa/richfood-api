package com.richfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richfood.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

}
