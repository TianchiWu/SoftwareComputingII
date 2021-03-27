package com.example.hotel.bl.room;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.RoomList;
import com.example.hotel.entity.ResponseVO;
import java.util.List;

public interface RoomService {
    /**
     * 预订酒店后更新客房房间数量(Room)
     * @param roomId
     * @param rooms
     */
    void updateRoomInfo(Integer roomId, Integer rooms);

    /**
     * 减少房间数，房间数等于0时删除此类房间(Room)
     * @param num
     * @return
     */
    ResponseVO updateRoomNum(int roomId,int num);

    /**
     * 获取指定酒店房间列表(RoomList)
     * @param hotelId
     * @return
     */
    List<RoomList> getRoomList(int hotelId);

    /**
     * 获取指定酒店房间类型(Room)
     * @param hotelId
     * @return
     */
    List<Room> getActiveRoomType(int hotelId);

    /**
     * 删除指定房间(RoomList)
     * @param roomListId
     */
    ResponseVO deleteRoom(int roomListId);

    /**
     * 按类型删除指定数量房间(RoomList)
     * @param roomTypeId
     * @param num
     * @return
     */
    ResponseVO deleteRooms(int roomTypeId,int num);

    /**
     * 获取此房型首个空房房号(RoomList)
     * @param roomId
     * @return
     */
    Integer getActiveRoom(int roomId);

    /**
     * 获取此房间类型已有的订单号(Room)
     * @param roomTypeId
     * @return
     */
    List<Integer> getOldOrderIds(int roomTypeId);

    /**
     * 获取酒店的房间类型列表(Room)
     * @param hotelId
     * @return
     */
    List<Room> getRoomsByHotel(int hotelId);

    /**
     * 根据订单获取此订单对应的第一个房间(RoomList)
     * @param orderId
     * @return
     */
    Integer getFirstByOrderId(int orderId);

    /**
     * 更新房间当前所剩数量(Room)
     * 不影响总量
     * @param roomId
     * @param changeNum
     */
    void updateRoomCurNum(int roomId,int changeNum);

    /**
     * 更改房间状态(RoomList)
     * @param orderId
     * @param state
     */
    void updateRoomListState(int orderId,String state);

    /**
     * 匹配房间(RoomList)
     * @param roomListId
     * @param orderId
     */
    void matchRoom(int roomListId,int orderId);

    /**
     * 获得某种房间总数(Room)
     * @param roomId
     * @return
     */
    int getRoomTotal(int roomId);

    /**
     * 添加新房间(Room),(RoomList)
     * @param hotelRoom
     */
    void addRoomInfo(Room hotelRoom);
}
