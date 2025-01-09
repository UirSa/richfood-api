package com.richfood.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BrowsingHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer historyId;
	
	private Integer userId;
	private Integer restaurantId;
	private LocalDateTime viewedAt;
	
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public LocalDateTime getViewedAt() {
		return viewedAt;
	}
	public void setViewedAt(LocalDateTime viewedAt) {
		this.viewedAt = viewedAt;
	}
//-------------------------------------------------------------------------------------------------
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
	private Users user;

	@ManyToOne
	@JoinColumn(name = "restaurantId", referencedColumnName = "restaurantId", insertable = false, updatable = false)
	private Restaurants restaurant;

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Restaurants getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurants restaurant) {
		this.restaurant = restaurant;
	}

	
	
	
}
