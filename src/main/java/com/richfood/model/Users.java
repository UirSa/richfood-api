package com.richfood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;
    @Column(name = "users_account")
    private String userAccount;
    private String password;
    private String tel;
    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //------------------------------------------------------------
    @OneToMany(mappedBy = "users")
    private List<Reviews> reviews;

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    //-------------------------------------------------------------
    @OneToMany(mappedBy = "users")
    private List<FavoriteRestaurants> favoriteRestaurants;

    public List<FavoriteRestaurants> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    public void setFavoriteRestaurants(List<FavoriteRestaurants> favoriteRestaurants) {
        this.favoriteRestaurants = favoriteRestaurants;
    }

    //---------------------------------------------------------
    @OneToMany(mappedBy = "users")
    private List<CouponsOrders> couponsOrders;

    public List<CouponsOrders> getCouponsOrders() {
        return couponsOrders;
    }

    public void setCouponsOrders(List<CouponsOrders> couponsOrders) {
        this.couponsOrders = couponsOrders;
    }

    //--------------------------------------------------------------
    @OneToMany(mappedBy = "users")
    private List<Reservations> reservations;

    public List<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservations> reservations) {
        this.reservations = reservations;
    }
}
