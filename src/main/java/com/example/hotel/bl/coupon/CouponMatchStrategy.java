package com.example.hotel.bl.coupon;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
public interface CouponMatchStrategy {
    /**
     * 优惠券匹配
     * @param coupon
     * @return
     */
    boolean isMatch(OrderList orderList, Coupon coupon);

    /**
     * 计算最终价格
     * @param coupon
     * @return
     */
    double calculateFinalPrice(OrderList orderList,Coupon coupon);

}
