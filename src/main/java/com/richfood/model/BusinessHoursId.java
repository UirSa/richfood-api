package com.richfood.model;

import com.richfood.util.StringToOffsetTimeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public class BusinessHoursId {

    private Integer restaurantId;
    private String dayOfWeek;
    @Convert(converter = StringToOffsetTimeConverter.class)
    private String startTime;

    public BusinessHoursId() {
    }

    public BusinessHoursId(Integer restaurantId, String dayOfWeek, String startTime) {
        this.restaurantId = restaurantId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
