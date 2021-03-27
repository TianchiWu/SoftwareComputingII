package com.example.hotel.bl.coupon;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
import com.example.hotel.entity.ResponseVO;
import java.util.List;

public interface CouponService {
    /**
     * 添加酒店特惠策略
     * @param coupon
     * @return
     */
    Coupon addCoupon(Coupon coupon);

    /**
     * 删除策略
     * @param id
     * @return
     */
    ResponseVO deleteCoupon(int id);

    /**
     * 返回某一订单可用的优惠策略列表
     * @return
     */
    List<Coupon> getMatchOrderCoupon(OrderList orderlist);

    /**
     * 查看某个酒店提供的所有优惠策略
     * @param hotelId
     * @return
     */
    List<Coupon> getHotelAllCoupon(Integer hotelId);

    /**
     * 获取网站管理员可操作的优惠券
     * @return
     */
    List<Coupon> getSalesCouponList();
}
