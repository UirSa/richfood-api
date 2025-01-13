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
    Page<Restaurants> findByCountry(String country, Pageable pageable);
    @Query("SELECT r FROM Restaurants r JOIN r.categories c WHERE c.name = :categoryName")
    Page<Restaurants> findRestaurantsByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
// SQL分類
//SELECT r.restaurant_id, r.name, r.address
//FROM restaurants r
//JOIN restaurant_categories rc ON r.restaurant_id = rc.restaurant_id
//JOIN categories c ON rc.category_id = c.category_id
//WHERE c.name = '燒肉';