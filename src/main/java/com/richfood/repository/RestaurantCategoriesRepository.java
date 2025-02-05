package com.richfood.repository;

import com.richfood.model.RestaurantCategories;
import com.richfood.model.RestaurantCategoriesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantCategoriesRepository extends JpaRepository<RestaurantCategories, RestaurantCategoriesId> {
}
