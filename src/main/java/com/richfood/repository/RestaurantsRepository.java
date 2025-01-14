package com.richfood.repository;


import com.richfood.model.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants, Integer> {
    Page<Restaurants> findRestaurantsByCountry(String country, Pageable pageable);
    @Query("SELECT r FROM Restaurants r JOIN r.categories c WHERE c.name = :categoryName")
    Page<Restaurants> findRestaurantsByCategoryName(@Param("categoryName") String category, Pageable pageable);
    @Query("SELECT r FROM Restaurants r JOIN r.categories c WHERE (:country IS NULL OR r.country = :country) AND (:categoryName IS NULL OR c.name = :categoryName)")
    Page<Restaurants> findRestaurantsByCountryAndCategoryName(String country, @Param("categoryName") String category, Pageable pageable);
}