package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_categories")
public class RestaurantCategories {
    @EmbeddedId
    private RestaurantCategoriesId restaurantCategoriesId;

    public RestaurantCategories(){}

    public RestaurantCategories(RestaurantCategoriesId restaurantCategoriesId) {
        this.restaurantCategoriesId = restaurantCategoriesId;
    }

    public RestaurantCategoriesId getRestaurantCategoriesId() {
        return restaurantCategoriesId;
    }

    public void setRestaurantCategoriesId(RestaurantCategoriesId restaurantCategoriesId) {
        this.restaurantCategoriesId = restaurantCategoriesId;
    }

    //-----------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    @JsonIgnore
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    //--------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnore
    private Categories categories;

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
