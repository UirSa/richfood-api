package com.richfood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "category_id")
    private Integer categoryId;

    private String name;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //-------------------------------------------------
    @OneToMany(mappedBy = "categories")
    private List<RestaurantCategories> restaurantCategories;

    public List<RestaurantCategories> getRestaurantCategories() {
        return restaurantCategories;
    }

    public void setRestaurantCategories(List<RestaurantCategories> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }
}
