package com.example.hotel.bl.order;
import com.example.hotel.entity.OrderList;
import com.example.hotel.entity.ResponseVO;
import java.util.List;

public interface OrderService {
    boolean isHotelHaveActiveOrder(int hotelId);
    /**
     * 预订酒店
     * @return
     */
    ResponseVO addOrder(OrderList orderList);

    /**
     * 获取此酒店所有订单信息
     * @param hotelId
     * @return
     */
    List<OrderList> getOrdersByHotelId(int hotelId);
    /**
     * 获得所有异常订单信息
     * @return
     */
    List<OrderList> getAbnormalOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<OrderList> getUserOrders(int userid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 获取入住过的酒店的订单
     * @param hotelId
     * @param userId
     * @return
     */
    boolean hasUserHotelOrders(int hotelId,int userId);

    /**
     * 获取某酒店的所有订单
     * @param hotelId
     * @return
     */
   List<OrderList> getHotelOrders(Integer hotelId);

    /**
     * 获取某一订单全部信息
     * @param id
     * @return
     */
   OrderList getOrderById(int id);

    /**
     * checkin
     * @param orderid
     * @return
     */
   ResponseVO checkin(int orderid);

    /**
     * checkout
     * @param orderid
     * @return
     */
    ResponseVO checkout(int orderid);

    /**
     * 获取某状态订单涉及的所有酒店的id列表
     * @param userId
     * @param orderstate
     * @return
     */
    List<Integer> getHotelIdByUserIdAndOrderState(int userId,String orderstate);

    /**
     * 检查异常订单以及分配房间
     */
    void checkOrder();

}
