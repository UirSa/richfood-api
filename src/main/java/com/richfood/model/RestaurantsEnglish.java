package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class RestaurantsEnglish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "restaurant_id")
    private Integer restaurantId;

    private String name;
    private String description;
    private String country;
    private String district;
    private String address;
    private Double score;
    private Integer average;
    private String image;
    private String phone;
    private double longitude;
    private double latitude;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //-----------------------------------------------------------
    @OneToMany(mappedBy = "restaurantsEnglish", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BusinessHoursEnglish> businessHoursEnglishes;

    public List<BusinessHoursEnglish> getBusinessHoursEnglishes() {
        return businessHoursEnglishes;
    }

    public void setBusinessHoursEnglishes(List<BusinessHoursEnglish> businessHoursEnglishes) {
        this.businessHoursEnglishes = businessHoursEnglishes;
    }

    //-----------------------------------------------
    @ManyToMany
    @JoinTable(
            name = "restaurant_categories_english",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoriesEnglish> categoriesEnglishes;

    public List<CategoriesEnglish> getCategoriesEnglishes() {
        return categoriesEnglishes;
    }

    public void setCategoriesEnglishes(List<CategoriesEnglish> categoriesEnglishes) {
        this.categoriesEnglishes = categoriesEnglishes;
    }
}
