package com.richfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private Integer restaurantId;
    private Integer userId;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private Integer storeId;
    private Boolean isFlagged;
    private Boolean isApproved;


    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

   

    public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsFlagged() {
		return isFlagged;
	}

	public void setIsFlagged(Boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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
    @OneToMany(mappedBy = "reviews", cascade = CascadeType.ALL)
    @JsonIgnore
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
    @JsonIgnore
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
