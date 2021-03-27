package com.example.hotel.repository;

import com.example.hotel.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepository  extends JpaRepository<Coupon,Integer> {
    @Transactional
    List<Coupon> findAllByHotelId(int hotelId);
}
