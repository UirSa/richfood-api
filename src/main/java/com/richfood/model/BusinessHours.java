package com.richfood.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalTime;

@Entity
public class BusinessHours {

    @EmbeddedId
    private BusinessHoursId businessHoursId;

    private String end_time;

    public BusinessHoursId getBusinessHoursId() {
        return businessHoursId;
    }

    public void setBusinessHoursId(BusinessHoursId businessHoursId) {
        this.businessHoursId = businessHoursId;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    //-----------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }
}
