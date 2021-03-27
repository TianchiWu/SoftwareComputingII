package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

   @PostMapping("/addCoupon")
   public ResponseVO addHotelTimeCoupon(@RequestBody Coupon coupon){
       Coupon couponVO = couponService.addCoupon(coupon);
       return ResponseVO.buildSuccess(couponVO);
   }


    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut,
                                           @RequestParam String birthday) {
        OrderList requestOrderVO = new OrderList();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        requestOrderVO.setBirthday(birthday);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }

    @GetMapping("/{id}/deleteCoupon")
    public ResponseVO deleteCoupon(@PathVariable int id) { return ResponseVO.buildSuccess(couponService.deleteCoupon(id)); }

    @GetMapping("salesAllCoupons")
    public ResponseVO getSalesAllCoupons(){ return ResponseVO.buildSuccess(couponService.getSalesCouponList()); }
}
