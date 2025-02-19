package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    private Integer userId;
    private String reservationDate;
    private String reservationTime;
    private Integer numPeople;
    private OffsetDateTime editTime;
    private Boolean state;
    private Integer storeId;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    public OffsetDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(OffsetDateTime editTime) {
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
//    @JsonIgnore
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
    //@JsonIgnore
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
