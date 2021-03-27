package com.example.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="username")
    private String userName;
    @Column(name="phonenumber")
    private String phoneNumber;
    @Column(name="credit")
    private Double credit;
    @Column(name="usertype")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name="birthday")
    private String birthday;
    @Column(name="membertype")
    private String membertype;
    @Column(name="memberlevel")
    private String memberlevel;
    @Column(name="enterprisename")
    private String enterprisename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getBirthday(){ return birthday; }

    public void setBirthday(String birthday){ this.birthday=birthday; }


    public String getMembertype(){ return membertype; }

    public void setMembertype(String membertype){ this.membertype = membertype; }

    public String getMemberlevel() { return memberlevel; }

    public void setMemberlevel(String memberlevel){ this.memberlevel=memberlevel; }

    public String getEnterprisename() { return enterprisename; }

    public void setEnterprisename(String enterprisename) { this.enterprisename = enterprisename; }

}
