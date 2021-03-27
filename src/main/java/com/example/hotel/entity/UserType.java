package com.example.hotel.entity;

public enum UserType {
    Client("1"),
    HotelManager("2"),
    Manager("3"),
    Sale("4");

    private String value;

    UserType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }

}
