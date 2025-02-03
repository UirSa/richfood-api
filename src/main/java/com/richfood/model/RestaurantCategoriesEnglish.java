package com.richfood.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_categories_english")
public class RestaurantCategoriesEnglish {
    @EmbeddedId
    private RestaurantCategoriesId restaurantCategoriesId;

    public RestaurantCategoriesEnglish() {}
    public RestaurantCategoriesEnglish(RestaurantCategoriesId restaurantCategoriesId) {
        this.restaurantCategoriesId = restaurantCategoriesId;
    }

    public RestaurantCategoriesId getRestaurantCategoriesId() {
        return restaurantCategoriesId;
    }

    public void setRestaurantCategoriesId(RestaurantCategoriesId restaurantCategoriesId) {
        this.restaurantCategoriesId = restaurantCategoriesId;
    }
}
