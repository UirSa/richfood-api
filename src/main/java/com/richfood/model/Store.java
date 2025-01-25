package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    private Integer restaurantId;
    private String storeAccount;
    private String password;
    private byte[] icon;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getStoreAccount() {
        return storeAccount;
    }

    public void setStoreAccount(String storeAccount) {
        this.storeAccount = storeAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	//------------------------------------
    @OneToOne
    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
//    @JsonIgnore
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    //--------------------------------------------
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservations> reservations;

    public List<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservations> reservations) {
        this.reservations = reservations;
    }

    //---------------------------------------------------------
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Coupons> coupons;

    public List<Coupons> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupons> coupons) {
        this.coupons = coupons;
    }

    //------------------------------------------------------
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CouponsOrders> couponsOrders;

    public List<CouponsOrders> getCouponsOrders() {
        return couponsOrders;
    }

    public void setCouponsOrders(List<CouponsOrders> couponsOrders) {
        this.couponsOrders = couponsOrders;
    }
    //-------------------------------------------------------
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RestaurantCapacity> restaurantCapacities;

    public List<RestaurantCapacity> getRestaurantCapacities() {
        return restaurantCapacities;
    }

    public void setRestaurantCapacities(List<RestaurantCapacity> restaurantCapacities) {
        this.restaurantCapacities = restaurantCapacities;
    }
}
