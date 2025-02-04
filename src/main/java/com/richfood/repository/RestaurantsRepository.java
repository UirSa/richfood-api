package com.richfood.repository;


import com.richfood.model.Restaurants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants, Integer> {
    Page<Restaurants> findRestaurantsByCountry(String country, Pageable pageable);
    @Query("SELECT r FROM Restaurants r JOIN r.categories c WHERE c.name = :categoryName")
    Page<Restaurants> findRestaurantsByCategoryName(@Param("categoryName") String category, Pageable pageable);
    @Query("SELECT r FROM Restaurants r JOIN r.categories c WHERE (:country IS NULL OR r.country = :country) AND (:categoryName IS NULL OR c.name = :categoryName)")
    Page<Restaurants> findRestaurantsByCountryAndCategoryName(String country, @Param("categoryName") String category, Pageable pageable);
    @Query("SELECT r FROM Restaurants r WHERE r.latitude BETWEEN :latMax AND :latMin AND r.longitude BETWEEN :longMax AND :longMin")
    Page<Restaurants> findRestaurantsByLatitudeAndLongitudeBetween(@Param("latMin") double latMin, @Param("latMax") double latMax, @Param("longMin") double longMin, @Param("longMax") double longMax, Pageable pageable);
    @Query("SELECT r FROM Restaurants r WHERE r.latitude BETWEEN :latMax AND :latMin AND r.longitude BETWEEN :longMax AND :longMin")
    List<Restaurants> findRestaurantsByLatitudeAndLongitudeBetween(@Param("latMin") double latMin, @Param("latMax") double latMax, @Param("longMin") double longMin, @Param("longMax") double longMax);
    @Query("SELECT r.name FROM Restaurants r WHERE r.restaurantId = :restaurantId")
    String findNameByRestaurantId(@Param("restaurantId") Integer restaurantId);
    @Query(value = "SELECT * FROM Restaurants WHERE description LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    Page<Restaurants> findRestaurantsByKeyword(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT r.image FROM Restaurants r WHERE r.restaurantId = :restaurantId")
    String findImageByRestaurantId(@Param("restaurantId") Integer restaurantId);

}

//建立搜尋索引
//CREATE INDEX idx_restaurant_fulltext
//ON restaurants
//USING gin(to_tsvector('simple', name || ' ' || description || ' ' || country || ' '||district||' ' ||address));