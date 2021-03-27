package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="hotel")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="hotel_name")
    private String hotelName;
    @Column(name="hotel_description")
    private String description;
    @Column(name="address")
    private String address;
    @Column(name="hotel_star")
    private String hotelStar;
    @Column(name="phone_num")
    private String phoneNum;
    @Column(name="manager_id")
    private int managerId;
    @Column(name="information")
    private String information;
    @Transient
    private Double rate;
    @Transient
    private  List<Room> rooms;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
