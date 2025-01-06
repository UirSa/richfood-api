package com.richfood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ReviewsId;

    private Integer restaurantId;
    private Integer userId;
    private Integer rating;
    private String context;
    private String createdAt;
    private Boolean isFlagged;
    private Boolean isApproved;


    public Integer getReviewsId() {
        return ReviewsId;
    }

    public void setReviewsId(Integer reviewsId) {
        ReviewsId = reviewsId;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getFlagged() {
        return isFlagged;
    }

    public void setFlagged(Boolean flagged) {
        isFlagged = flagged;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    //---------------------------------------------------------
    @OneToMany(mappedBy = "reviews")
    private List<ReviewAudits> reviewAudits;

    public List<ReviewAudits> getReviewAudits() {
        return reviewAudits;
    }

    public void setReviewAudits(List<ReviewAudits> reviewAudits) {
        this.reviewAudits = reviewAudits;
    }

    //-----------------------------------------------------------
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
