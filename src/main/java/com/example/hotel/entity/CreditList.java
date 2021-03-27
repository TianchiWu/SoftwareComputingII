package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="creditlist")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class CreditList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private int userId;
    @Column(name="cur_date")
    private String curDate;
    @Column(name="order_id")
    private int orderId;
    @Column(name="Action")
    private String action;
    @Column(name="credit_before")
    private double CreditBefore;
    @Column(name="credit_after")
    private double CreditAfter;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCreditBefore(double creditBefore) {
        CreditBefore = creditBefore;
    }

    public double getCreditBefore() {
        return CreditBefore;
    }

    public void setCreditAfter(double creditAfter) {
        CreditAfter = creditAfter;
    }

    public double getCreditAfter() {
        return CreditAfter;
    }
}
