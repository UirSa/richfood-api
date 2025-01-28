package com.richfood.repository;

import com.richfood.model.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	 Optional<Users> findByName(String name);
	  Optional<Users> findByUserAccount(String userAccount); 
	  Optional<Users> findByEmail(String Email); 
	  Optional<Users> findById(Integer userId);
	  
	  @Query("SELECT u.name FROM Users u WHERE u.id = :userId")
	  String findUserNameByUserId(@Param("userId") Integer userId);
}
