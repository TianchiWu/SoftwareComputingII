package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
import org.springframework.stereotype.Service;

@Service
public class BirthdayCouponStrategylmpl implements CouponMatchStrategy {


    @Override
    public boolean isMatch(OrderList orderList, Coupon coupon) {
        if (coupon.getCouponType() == 1 && orderList.getCheckInDate().substring(5,7).equals(orderList.getBirthday().substring(5,7))) {
                if(coupon.getDiscount()==-1){
                    return orderList.getPrice() >= coupon.getTargetMoney();
                }
                else{
                    return true;
                }
            }
        return false;
    }

    @Override
    public double calculateFinalPrice(OrderList orderList, Coupon coupon){
        double price=orderList.getPrice();
        if(coupon.getDiscount()==-1){
            price-=coupon.getDiscountMoney();
        }
        else{
            price*=coupon.getDiscount()/10;
        }
        return price;
    }
}
