package com.richfood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;

    private Integer restaurantId;
    private String name;
    private String description;
    private String createdAt;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //-------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //---------------------------------------------------------
    @OneToMany(mappedBy = "coupons")
    private List<CouponsOrders> couponsOrders;

    public List<CouponsOrders> getCouponsOrders() {
        return couponsOrders;
    }

    public void setCouponsOrders(List<CouponsOrders> couponsOrders) {
        this.couponsOrders = couponsOrders;
    }
}
