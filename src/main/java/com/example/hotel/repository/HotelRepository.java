package com.example.hotel.repository;


import com.example.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    @Transactional
    List<Hotel> findAllByManagerId(int managerId);

}
