package com.richfood.model;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.Date;

@Entity
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    private Integer restaurantId;
    private Integer userId;
    private Date reservationDate;
    private Time reservationTime;
    private String numPeople;
    private Time editTime;
    private Boolean state;
    private Integer storeId;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Time reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(String numPeople) {
        this.numPeople = numPeople;
    }

    public Time getEditTime() {
        return editTime;
    }

    public void setEditTime(Time editTime) {
        this.editTime = editTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    //------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "storeId", insertable = false, updatable = false)
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //---------------------------------------------------------
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
