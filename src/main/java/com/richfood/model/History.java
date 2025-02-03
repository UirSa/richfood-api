package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;

    private Integer restaurantId;
    private LocalDateTime viewedAt;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    //---------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
    @JsonIgnore
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }
}
