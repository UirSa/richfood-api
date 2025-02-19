package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Restaurants {

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
    private Integer storeId;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    //-------------------------------------------------------
    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<BusinessHours> businessHours;

    public List<BusinessHours> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(List<BusinessHours> businessHours) {
        this.businessHours = businessHours;
    }

    //----------------------------------------------------------
    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RestaurantCategories> restaurantCategories;

    public List<RestaurantCategories> getRestaurantCategories() {
        return restaurantCategories;
    }

    public void setRestaurantCategories(List<RestaurantCategories> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }

    @ManyToMany
    @JoinTable(
            name = "restaurant_categories",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")  
    )
//  @JsonIgnore
    private List<Categories> categories;

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    //-------------------------------------------------------
    @OneToOne(mappedBy = "restaurants", cascade = CascadeType.ALL)
    @JsonIgnore
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //-----------------------------------------------------------
//    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<RestaurantCapacity> restaurantCapacity;
//
//    public List<RestaurantCapacity> getRestaurantCapacity() {
//        return restaurantCapacity;
//    }
//
//    public void setRestaurantCapacity(List<RestaurantCapacity> restaurantCapacity) {
//        this.restaurantCapacity = restaurantCapacity;
//    }
    //--------------------------------------------------------
    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<History> histories;

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }
}
