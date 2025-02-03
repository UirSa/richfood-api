package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CategoriesEnglish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "category_id")
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

    //-----------------------------------------------
    @ManyToMany
    @JsonIgnore
    private List<RestaurantsEnglish> restaurantsEnglish;

    public List<RestaurantsEnglish> getRestaurantsEnglish() {
        return restaurantsEnglish;
    }

    public void setRestaurantsEnglish(List<RestaurantsEnglish> restaurantsEnglish) {
        this.restaurantsEnglish = restaurantsEnglish;
    }
}
