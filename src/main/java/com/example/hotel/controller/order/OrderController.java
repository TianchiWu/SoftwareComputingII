package com.example.hotel.controller.order;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.OrderList;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CreditService creditService;

    @Autowired
    private HotelService hotelService;


    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderList orderList){
        return orderService.addOrder(orderList);
    }

    @GetMapping("/{userId}/getAllOrders")
    public ResponseVO retrieveAllOrders(@PathVariable int userId){
        List<Hotel> hotelList=hotelService.getHotelsByManagerId(userId);
        List<OrderList> orderList = null;
        for(int i=0;i<hotelList.size();i++){

            List<OrderList> getorderbyid=orderService.getOrdersByHotelId(hotelList.get(i).getId());
            if(orderList==null){
                orderList=getorderbyid;
            }
            else {
                for (int j = 0; j < getorderbyid.size(); j++) {
                    orderList.add(getorderbyid.get(j));
                }
            }
        }

        return ResponseVO.buildSuccess(orderList);
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }
    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }
    @GetMapping("/{orderid}/getOrderById")
    public ResponseVO retrieveIdOrders(@PathVariable int orderid){
        return ResponseVO.buildSuccess(orderService.getOrderById(orderid));
    }
    @GetMapping("/{orderid}/checkin")
    public ResponseVO finishOrder(@PathVariable int orderid){
        return orderService.checkin(orderid);
    }

    @GetMapping("/{orderid}/checkout")
    public ResponseVO checkout(@PathVariable int orderid){
        OrderList order=orderService.getOrderById(orderid);
        //我解不开这耦合
        creditService.CreditChangeDetal(order.getUserId(), orderid, order.getPrice() / 20, "完成订单");
        return orderService.checkout(orderid);
    }
    @GetMapping("/getAbnormalOrder")
    public ResponseVO getAbnormalOrder(){
        return ResponseVO.buildSuccess(orderService.getAbnormalOrders());
    }


}
