package com.example.hotel.entity;

public class HotelSearchVO {
    private int userId;
    private double maxrate;
    private double minrate;
    private String hotelStar;
    private String address;
    private String hotelName;
    private String orderState;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(double maxrate) {
        this.maxrate = maxrate;
    }

    public double getMinrate() {
        return minrate;
    }

    public void setMinrate(double minrate) {
        this.minrate = minrate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
