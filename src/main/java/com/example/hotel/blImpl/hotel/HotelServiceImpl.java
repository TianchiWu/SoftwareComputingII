package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.comment.CommentService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.room.RoomService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.*;
import com.example.hotel.repository.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String SET_Failure = "不是酒店管理员账号";
    private final static String UPDATE_ERROR = "修改失败";

    @Autowired
    private UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    RoomService roomService;
    @Autowired
    OrderService orderService;
    @Autowired
    HotelRepository hotelRepository;
    @Override
    public ResponseVO addHotel(Hotel hotel) {
        List<Hotel> hotels=hotelRepository.findAll();
        for (Hotel value : hotels) {
            if (hotel.getAddress().equals(value.getAddress())) {
                return ResponseVO.buildFailure("此地址已有酒店");
            }
        }
        hotelRepository.save(hotel);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteHotel(int hotelId) {
        if(orderService.isHotelHaveActiveOrder(hotelId)){
            hotelRepository.deleteById(hotelId);
            return ResponseVO.buildSuccess(true);
        }
            return ResponseVO.buildFailure("此酒店有正在执行中的订单，不能被删除");
    }


    @Override
    public List<Hotel> retrieveHotels(HotelSearchVO hotelSearchVO) {
        List<Hotel> result=hotelRepository.findAll();
        String targetAdress=hotelSearchVO.getAddress();
        double maxrate=hotelSearchVO.getMaxrate();
        double minrate=hotelSearchVO.getMinrate();
        String hotelName=hotelSearchVO.getHotelName();
        String hotelStar=hotelSearchVO.getHotelStar();
        String orderState=hotelSearchVO.getOrderState();
        List<Integer> hotels=new ArrayList<>();
        if(orderState==null){
            orderState="";
        }
        switch (orderState) {
            case "已退房":{
                hotels=orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"已退房");
                List<Integer> target=orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"已撤销");
                hotels.addAll(target);
                for (int i = 0; i < result.size(); i++) {
                    if (!hotels.contains(result.get(i).getId())) {
                        result.remove(i);
                        i--;
                    }
                }
                break;
            }
            case "已入住": {
                hotels=orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"已入住");
                for (int i = 0; i < result.size(); i++) {
                    if (!hotels.contains(result.get(i).getId())) {
                        result.remove(i);
                        i--;
                    }
                }
                break;
            }
            case "异常": {
                hotels=orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"入住异常");
                hotels.addAll(orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"退房异常"));
                for (int i = 0; i < result.size(); i++) {
                    if (!hotels.contains(result.get(i).getId())) {
                        result.remove(i);
                        i--;
                    }
                }
                break;
            }
            case "无订单": {
                hotels=orderService.getHotelIdByUserIdAndOrderState(hotelSearchVO.getUserId(),"");
                for (int i = 0; i < result.size(); i++) {
                    if (hotels.contains(result.get(i).getId())) {
                        result.remove(i);
                        i--;
                    }
                }
                break;
            }
        }
        if(targetAdress==null){
            targetAdress="";
        }
        if(hotelName==null){
            hotelName="";
        }
        if(hotelStar==null){
            hotelStar="";
        }
        if(!targetAdress.equals("")){
            for(int i=0;i<result.size();i++){
                if(!result.get(i).getAddress().contains(targetAdress)){
                    result.remove(i);
                    i--;
                }
            }
        }
        if(!hotelName.equals("")){
            for(int i=0;i<result.size();i++){
                if(!result.get(i).getHotelName().contains(hotelName)){
                    result.remove(i);
                    i--;
                }
            }
        }
        if(!hotelStar.equals("")){
            for(int i=0;i<result.size();i++){
                if(!result.get(i).getHotelStar().equals(hotelStar)){
                    result.remove(i);
                    i--;
                }
            }
        }
        for (Hotel hotel : result) {
            Double rate = commentService.getRate(hotel.getId());
            hotel.setRate(rate);
        }
        for(int i=0;i<result.size();i++){
            Double rate=result.get(i).getRate();
            if(rate<minrate||rate>maxrate){
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    @Override
    public List<Hotel> getHotelsByManagerId(int userId){
        List<Hotel> result=hotelRepository.findAllByManagerId(userId);
        for (Hotel hotel : result) {
            Double rate = commentService.getRate(hotel.getId());
            hotel.setRate(rate);
        }
        return result;
    }

    @Override
    public Hotel retrieveHotelDetails(Integer hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).get();
        hotel.setRate(commentService.getRate(hotelId));
        hotel.setRooms(roomService.getRoomsByHotel(hotelId));
        return hotel;
    }

    @Override
    public void deleteHotelManager(int id){
        List<Hotel> hotels=hotelRepository.findAllByManagerId(id);
        for (Hotel hotel : hotels) {
            hotel.setManagerId(0);
        }
        hotelRepository.saveAll(hotels);
    }

    @Override
    public ResponseVO setManager(int hotelid,int managerId){
        Hotel hotel=hotelRepository.getOne(hotelid);
        if(managerId==0){
            hotel.setManagerId(0);
            hotelRepository.save(hotel);
            return ResponseVO.buildSuccess("重置管理员成功");
        }
        if(userService.isHotelManager(managerId)) {
                hotel.setManagerId(managerId);
                hotelRepository.save(hotel);
                return ResponseVO.buildSuccess(true);
        }
        else{
            return ResponseVO.buildFailure(SET_Failure);
        }
    }

    @Override
    public ResponseVO  updateHotelInfo(int id, String hotelName, String hotelDescription, String address, String hotelStar, String phoneNum,String information){
        try {
            List<Hotel> hotels=hotelRepository.findAll();
            for (Hotel value : hotels) {
                if (address.equals(value.getAddress())&&id!=value.getId()) {
                    return ResponseVO.buildFailure("此地址已有酒店");
                }
            }
            Hotel hotel=hotelRepository.getOne(id);
            hotel.setHotelName(hotelName);
            hotel.setDescription(hotelDescription);
            hotel.setAddress(address);
            hotel.setHotelStar(hotelStar);
            hotel.setPhoneNum(phoneNum);
            hotel.setInformation(information);
            hotelRepository.save(hotel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

}
