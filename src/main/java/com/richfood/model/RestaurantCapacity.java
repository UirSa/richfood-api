package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class RestaurantCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer capacityId;

    private Integer storeId;
    private String time;
    private Integer maxCapacity;
    private String date;

    public Integer getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Integer capacityId) {
        this.capacityId = capacityId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //------------------------------------------------
//    @ManyToOne
//    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
//    @JsonIgnore
//    private Restaurants restaurants;
//
//    public Restaurants getRestaurants() {
//        return restaurants;
//    }
//
//    public void setRestaurants(Restaurants restaurants) {
//        this.restaurants = restaurants;
//    }

    //-------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "storeId", insertable = false, updatable = false)
    @JsonIgnore
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
