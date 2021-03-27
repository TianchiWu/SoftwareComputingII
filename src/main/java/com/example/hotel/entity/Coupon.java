package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="coupon")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="description")
    private String description;
    @Column(name="hotel_id")
    private int hotelId;
    @Column(name="coupon_type")
    private int couponType;
    @Column(name="coupon_name")
    private String couponName;
    @Column(name="target_money")
    private int targetMoney;
    @Column(name="discount")
    private double discount;
    @Column(name="status")
    private int status;
    @Column(name="start_time")
    private String startTime;
    @Column(name="end_time")
    private String EndTime;
    @Column(name="discount_money")
    private int discountMoney;
    @Column(name="property")
    private int property;
    @Transient
    private double finalPrice;
    public void setId(int id) {
        this.id = id;
    }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setTargetMoney(int targetMoney) {
        this.targetMoney = targetMoney;
    }

    public int getTargetMoney() {
        return targetMoney;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setDiscountMoney(int discountMoney) {
        this.discountMoney = discountMoney;
    }

    public int getDiscountMoney() {
        return discountMoney;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public int getProperty() {
        return property;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
