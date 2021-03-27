package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="roomlist")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class RoomList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="hotel_id")
    private int hotelId;
    @Column(name="room_type")
    private String roomType;
    @Column(name="room_state")
    private String roomState;
    @Column(name="breakfast")
    private String breakfast;
    @Column(name="facility")
    private String facility;
    @Column(name="price")
    private double price;
    @Column(name="room_id")
    private int roomId;
    @Column(name="order_id")
    private Integer orderId;
    @Override
    public RoomList clone() throws CloneNotSupportedException {
        return (RoomList)super.clone();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getRoomState() {
        return roomState;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }
}
