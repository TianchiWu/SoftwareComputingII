package com.example.hotel.repository;
import com.example.hotel.entity.RoomList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RoomListRepository extends JpaRepository<RoomList,Integer> {
    @Transactional
    List<RoomList> findAllByHotelId(int hotelId);
    @Transactional
    List<RoomList> findAllByRoomIdAndRoomState(int roomId,String roomState);
    @Transactional
    List<RoomList> findAllByOrderId(int orderId);
    @Transactional
    List<RoomList> findAllByRoomId(int roomId);
}
