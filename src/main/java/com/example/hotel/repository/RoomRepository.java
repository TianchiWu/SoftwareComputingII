package com.example.hotel.repository;

import com.example.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer>{
    @Transactional
    List<Room> findAllByHotelId(int hotelId);
    @Transactional
    List<Room> findAllByBreakfastAndFacilityAndHotelIdAndPriceAndRoomType(String breakfast,String facility,int hotelId,double price,String roomType);
}
