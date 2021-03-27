package com.example.hotel.repository;
import com.example.hotel.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;
public interface OrderListRepository  extends JpaRepository<OrderList,Integer> {
    @Transactional
    List<OrderList> findAllByUserId(int userId);
    @Transactional
    List<OrderList> findAllByRoomIdAndOrderState(int roomId,String orderState);
    @Transactional
    List<OrderList> findAllByHotelId(int hotelId);
    @Transactional
    List<OrderList> findAllByHotelIdAndUserIdAndOrderState(int hotelId,int userId,String orderState);
    @Transactional
    List<OrderList> findAllByUserIdAndOrderState(int userId,String orderState);
    @Transactional
    List<OrderList> findAllByOrderState(String orderState);
}
