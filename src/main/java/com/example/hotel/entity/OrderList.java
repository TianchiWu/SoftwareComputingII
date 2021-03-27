package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="orderlist")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="hotel_id")
    private Integer hotelId;
    @Column(name="hotel_name")
    private String hotelName;
    @Column(name="checkin_date")
    private String checkInDate;
    @Column(name="checkout_date")
    private String checkOutDate;
    @Column(name="room_type")
    private String roomType;
    @Column(name="room_num")
    private Integer roomNum;
    @Column(name="people_num")
    private Integer peopleNum;
    @Column(name="have_child",columnDefinition = "TINYINT")
    private boolean haveChild;
    @Column(name="create_date")
    private String createDate;
    @Column(name="annul_date")
    private String annulDate;
    @Column(name="price",columnDefinition="decimal(65,1) Default null")
    private Double price;
    @Column(name="client_name")
    private String clientName;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="order_state")
    private String orderState;
    @Column(name="birthday")
    private String birthday;
    @Column(name="room_id")
    private Integer roomId;
    @Column(name="last")
    private Integer last;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }


    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setHaveChild(boolean haveChild) {
        this.haveChild = haveChild;
    }

    public boolean isHaveChild() {
        return haveChild;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setAnnulDate(String annulDate) {
        this.annulDate = annulDate;
    }

    public String getAnnulDate() {
        return annulDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public Integer getLast() {
        return last;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }
}
