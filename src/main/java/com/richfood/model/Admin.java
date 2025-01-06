package com.richfood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    private String account;
    private String password;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //------------------------------------------------------
    @OneToMany(mappedBy = "admin")
    private List<ReviewAudits> reviewAudits;

    public List<ReviewAudits> getReviewAudits() {
        return reviewAudits;
    }

    public void setReviewAudits(List<ReviewAudits> reviewAudits) {
        this.reviewAudits = reviewAudits;
    }
}
