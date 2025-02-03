package com.richfood.model;

import com.richfood.util.StringToOffsetTimeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "business_hours_english")
public class BusinessHoursEnglish {

    @EmbeddedId
    private BusinessHoursId businessHoursId;
    @Convert(converter = StringToOffsetTimeConverter.class)
    private String endTime;

    public BusinessHoursEnglish() {}

    public BusinessHoursEnglish(BusinessHoursId businessHoursId, String endTime) {
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
}
