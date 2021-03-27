package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="room")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="price")
    private double price;
    @Column(name="cur_num")
    private int curNum;
    @Column(name="total")
    private int total;
    @Column(name="hotel_id")
    private int hotelId;
    @Column(name="room_type")
    private String roomType;
    @Column(name="breakfast")
    private String breakfast;
    @Column(name="facility")
    private String facility;

    public void setId(int id) {
        this.id = id;
    }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setCurNum(int curNum) {
        this.curNum = curNum;
    }

    public int getCurNum() {
        return curNum;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFacility() {
        return facility;
    }
}
