package com.example.hotel.bl.hotel;
import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.HotelSearchVO;
import com.example.hotel.entity.ResponseVO;
import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotel
     * @throws
     */
    ResponseVO addHotel(Hotel hotel) ;

    /**
     * 删除酒店
     * @param hotelId
     * @throws
     */
    ResponseVO deleteHotel(int hotelId) ;

    /**
     * 更新酒店信息
     * @param id
     * @param hotelName
     * @param hotelDescription
     * @param address
     * @param hotelStar
     * @param phoneNum
     * @return
     */
    ResponseVO  updateHotelInfo(int id, String hotelName, String hotelDescription, String address, String hotelStar, String phoneNum,String information);

    /**
     * 设置酒店管理员管理员
     * @param hotelid
     * @return
     */
    ResponseVO setManager(int hotelid,int managerId);

    /**
     * 删除酒店管理员
     * @param id
     */

    void deleteHotelManager(int id);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<Hotel> retrieveHotels(HotelSearchVO hotelSearchVO);

    /**
     * 根据管理员id获取酒店列表
     * @param userId
     * @return
     */
    List<Hotel> getHotelsByManagerId(int userId);

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    Hotel retrieveHotelDetails(Integer hotelId);






}
