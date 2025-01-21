package com.richfood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RestaurantCategoriesId {
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Column(name = "category_id")
    private Integer categoryId;

    public RestaurantCategoriesId(){}

    public RestaurantCategoriesId(Integer restaurantId, Integer categoryId) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


}
