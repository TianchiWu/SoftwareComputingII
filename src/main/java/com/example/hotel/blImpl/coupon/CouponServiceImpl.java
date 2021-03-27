package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
import com.example.hotel.entity.ResponseVO;
import com.example.hotel.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {

    private final BirthdayCouponStrategylmpl birthdayCouponStrategylmpl;
    private final  TimeCouponStrategyImpl timeCouponStrategy;
    private final  MultiplyRoomCouponStrategylmpl multiplyRoomCouponStrategy;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(BirthdayCouponStrategylmpl birthdayCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             MultiplyRoomCouponStrategylmpl multiplyRoomCouponStrategy) {
        this.birthdayCouponStrategylmpl = birthdayCouponStrategy;
        this.multiplyRoomCouponStrategy = multiplyRoomCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(birthdayCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(multiplyRoomCouponStrategy);
    }
    @Autowired
    CouponRepository couponRepository;


    @Override
    public List<Coupon> getMatchOrderCoupon(OrderList orderList) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderList.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (Coupon hotelCoupon : hotelCoupons) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderList, hotelCoupon)) {
                    hotelCoupon.setFinalPrice(strategy.calculateFinalPrice(orderList, hotelCoupon));
                    availAbleCoupons.add(hotelCoupon);
                }
            }
        }

        List<Coupon> salesCoupon = couponRepository.findAllByHotelId(-1);
        for (Coupon coupon : salesCoupon) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderList, coupon)) {
                    coupon.setFinalPrice(strategy.calculateFinalPrice(orderList, coupon));
                    availAbleCoupons.add(coupon);
                }
            }
        }
        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponRepository.findAllByHotelId(hotelId);
        return hotelCoupons;
    }



    @Override
    public Coupon addCoupon(Coupon coupon){
        int result = couponRepository.save(coupon).getId();
        coupon.setId(result);
        return coupon;
    }


    @Override
    public ResponseVO deleteCoupon(int id){
        couponRepository.deleteById(id);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Coupon> getSalesCouponList(){
        return couponRepository.findAllByHotelId(-1);
    }
}
