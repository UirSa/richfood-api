package com.richfood.repository;

import com.richfood.model.Admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	 
	boolean existsByAdminAccount(String adminAccount);
	
	@Query("SELECT a.adminAccount FROM Admin a")
	List<String> findAllAdminAccount();
}
