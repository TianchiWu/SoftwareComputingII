package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.entity.Coupon;
import com.example.hotel.entity.OrderList;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    @Override
    public boolean isMatch(OrderList orderList, Coupon coupon) {
        int days=countMatchDays(orderList.getCheckInDate(),orderList.getCheckOutDate(),coupon.getStartTime(),coupon.getEndTime());
        if(days>0&&coupon.getCouponType()==3){
            if(coupon.getDiscount()==-1)
                return orderList.getPrice() >= coupon.getTargetMoney();
            else return true;
        }
        return false;
    }

    @Override
    public double calculateFinalPrice(OrderList orderList, Coupon coupon){
        int days=countMatchDays(orderList.getCheckInDate(),orderList.getCheckOutDate(),coupon.getStartTime(),coupon.getEndTime());
        double price=orderList.getPrice();
        int totalDays=differentDays(orderList.getCheckInDate(),orderList.getCheckOutDate());
        double dailyPrice=price/totalDays;//单价
        if(coupon.getDiscount()==-1){
            price-=coupon.getDiscountMoney();
        }
        else{
            price-=dailyPrice*days*(1-coupon.getDiscount()/10);
        }
        return price;
    }

    private int countMatchDays(String checkInDate,String checkOutDate,String startTime,String endTime){
        try {
            checkInDate = checkInDate.substring(0, 10);
            checkOutDate = checkOutDate.substring(0, 10);
            startTime = startTime.substring(0, 10);
            endTime = endTime.substring(0, 10);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date inDate = format.parse(checkInDate);
                Date outDate = format.parse(checkOutDate);
                Date startDate = format.parse(startTime);
                Date endDate = format.parse(endTime);
                int diffIO = differentDays(inDate, outDate);//in-out-diffDays
                int diffSE = differentDays(startDate, endDate);//start-end-diffDays
                Date max = differentDays(inDate, startDate) > 0 ? startDate : inDate;//小中取大
                Date min = differentDays(outDate, endDate) > 0 ? outDate : endDate; //大中取小
                if (differentDays(max, min) <= 0) return 0;
                else {
                    return Math.min(differentDays(max, min), Math.min(diffIO, diffSE));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    //计算两天相差的天数
    private int differentDays(Date date1,Date date2){
        return (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
    }

    private int differentDays(String day1,String day2){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1=format.parse(day1.substring(0,10));
            Date d2=format.parse(day2.substring(0,10));
            return differentDays(d1,d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
