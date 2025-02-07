package com.richfood.repository;

import com.richfood.model.Restaurants;
import com.richfood.model.RestaurantsEnglish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsEnglishRepository extends JpaRepository<RestaurantsEnglish, Integer> {
    Page<RestaurantsEnglish> findRestaurantsEnglishByCountry(String country, Pageable pageable);
    @Query("SELECT r FROM RestaurantsEnglish r JOIN r.categoriesEnglishes c WHERE c.name = :categoryName")
    Page<RestaurantsEnglish> findRestaurantsEnglishByCategoryName(@Param("categoryName") String category, Pageable pageable);
    @Query("SELECT r FROM RestaurantsEnglish r JOIN r.categoriesEnglishes c WHERE (:country IS NULL OR r.country = :country) AND (:categoryName IS NULL OR c.name = :categoryName)")
    Page<RestaurantsEnglish> findRestaurantsEnglishByCountryAndCategoryName(String country, @Param("categoryName") String category, Pageable pageable);

}
