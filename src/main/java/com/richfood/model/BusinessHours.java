package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richfood.util.StringToOffsetTimeConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "business_hours")
public class BusinessHours {



    @EmbeddedId
    private BusinessHoursId businessHoursId;
    @Convert(converter = StringToOffsetTimeConverter.class)
    private String endTime;

    public BusinessHours() {
    }

    public BusinessHours(BusinessHoursId businessHoursId, String endTime) {
        this.businessHoursId = businessHoursId;
        this.endTime = endTime;
    }

    public BusinessHoursId getBusinessHoursId() {
        return businessHoursId;
    }

    public void setBusinessHoursId(BusinessHoursId businessHoursId) {
        this.businessHoursId = businessHoursId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    //-----------------------------------------------------------
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
