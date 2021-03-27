package com.example.hotel.blImpl.room;

import com.example.hotel.bl.room.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.RoomList;
import com.example.hotel.repository.OrderListRepository;
import com.example.hotel.repository.RoomListRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {


    @Autowired
    private OrderService orderService;
    @Autowired
    RoomListRepository roomListRepository;
    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    RoomRepository roomRepository;

    @Override
    public void updateRoomInfo(Integer roomId, Integer rooms) {
        Room room=roomRepository.getOne(roomId);
        room.setCurNum(room.getCurNum()-rooms);
        roomRepository.save(room);
    }

    @Override
    public ResponseVO updateRoomNum(int roomId ,int num){
        int result=roomRepository.getOne(roomId).getTotal();
        if(result-num>0) {
            Room room=roomRepository.getOne(roomId);
            room.setCurNum(room.getCurNum()-num);
            room.setTotal(room.getTotal()-num);
            roomRepository.save(room);
            return ResponseVO.buildSuccess(true);
        }
        else{
            roomRepository.deleteById(roomId);
            return ResponseVO.buildSuccess(true);
        }
    }
    @Override
    public ResponseVO deleteRooms(int roomTypeId,int num){
        List<RoomList> target=roomListRepository.findAllByRoomIdAndRoomState(roomTypeId,"空房");
        for(int i=0;i<num;i++) {
            int id = target.get(i).getId();
            roomListRepository.deleteById(id);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public List<RoomList> getRoomList(int hotelId){
        orderService.checkOrder();
        return roomListRepository.findAllByHotelId(hotelId);
    }

    @Override
    public List<Integer> getOldOrderIds(int roomTypeId){
        List<RoomList> target=roomListRepository.findAllByRoomId(roomTypeId);
        List<Integer> oldOrderIds=new ArrayList<>();
        for (RoomList roomList : target) {
            if (!oldOrderIds.contains(roomList.getOrderId())) {
                oldOrderIds.add(roomList.getOrderId());
            }
        }
        return oldOrderIds;
    }
    @Override
    public List<Room> getActiveRoomType(int hotelId){
        orderService.checkOrder();
        return roomRepository.findAllByHotelId(hotelId);
    }

    @Override
    public ResponseVO deleteRoom(int roomListId){
        roomListRepository.deleteById(roomListId);
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public Integer getFirstByOrderId(int orderId){
        return roomListRepository.findAllByOrderId(orderId).get(0).getId();
    }


    @Override
    public Integer getActiveRoom(int roomId){
        List<RoomList> result=roomListRepository.findAllByRoomIdAndRoomState(roomId,"空房");
        if(result.size()==0){
            return null;
        }
        else{
            return result.get(0).getId();
        }
    }


    public List<Room> getRoomsByHotel(int hotelId){
        List<Room> rooms = roomRepository.findAllByHotelId(hotelId);
        for (Room room : rooms) {
            String roomType = room.getRoomType();
            switch (roomType) {
                case "BigBed":
                    roomType = "大床房";
                    break;
                case "DoubleBed":
                    roomType = "双床房";
                    break;
                case "Family":
                    roomType = "家庭房";
                    break;
            }
            room.setRoomType(roomType);
        }
        return rooms;
    }

    @Override
    public void updateRoomCurNum(int roomId,int changeNum){
        Room room=roomRepository.getOne(roomId);
        room.setCurNum(room.getCurNum()+changeNum);
        roomRepository.save(room);
    }
    @Override
    public void updateRoomListState(int orderId,String state){
        List<RoomList> roomLists=roomListRepository.findAllByOrderId(orderId);
        for (RoomList roomList : roomLists) {
            if(state.equals("空房")){
                roomList.setOrderId(null);
            }
            if(roomList.getRoomState().equals("空房")){
                roomList.setOrderId(orderId);
            }
            roomList.setRoomState(state);

        }
        roomListRepository.saveAll(roomLists);
    }
    @Override
    public void matchRoom(int roomListId,int orderId){
        RoomList roomList=roomListRepository.getOne(roomListId);
        roomList.setOrderId(orderId);
        roomList.setRoomState("已预订");
        roomListRepository.save(roomList);
    }
    @Override
    public int getRoomTotal(int roomId){
        return roomRepository.getOne(roomId).getTotal();
    }

    @Override
    public void addRoomInfo(Room hotelRoom){
        List<Room> isInRoom=isInRoom(hotelRoom);
        if(isInRoom.size()==0) {
            hotelRoom=insertRoomInfo(hotelRoom);
        }
        else{
            updateRoomNum(isInRoom.get(0).getId(),-hotelRoom.getTotal());
        }
        insertRooms(hotelRoom);
    }

    /**
     * 获取此种房间的种类
     * @param hotelRoom
     * @return
     */
    private List<Room> isInRoom(Room hotelRoom) {
        return  roomRepository.findAllByBreakfastAndFacilityAndHotelIdAndPriceAndRoomType(hotelRoom.getBreakfast(),hotelRoom.getFacility(),hotelRoom.getHotelId(),hotelRoom.getPrice(),hotelRoom.getRoomType());


    }

    /**
     * 添加新房型
     * @param hotelRoom
     * @return
     */
    private Room insertRoomInfo(Room hotelRoom) {
        return roomRepository.save(hotelRoom);
    }

    /**
     * 添加新房间
     * @param hotelRoom
     */
    private void insertRooms(Room hotelRoom){
        RoomList roomList=new RoomList();
        roomList.setRoomState("空房");
        roomList.setHotelId(hotelRoom.getHotelId());
        roomList.setRoomType(hotelRoom.getRoomType());
        roomList.setPrice(hotelRoom.getPrice());
        roomList.setBreakfast(hotelRoom.getBreakfast());
        roomList.setFacility(hotelRoom.getFacility());
        int roomId=hotelRoom.getId();
        roomList.setRoomId(roomId);

        for(int i=0;i<hotelRoom.getTotal();i++){
            List<RoomList> already=roomListRepository.findAll();
            if(already.size()==0){
                roomList.setId(1);
            }
            else{
                roomList.setId(already.get(already.size()-1).getId()+1);
            }
            roomListRepository.save(roomList);
        }

        orderService.checkOrder();
    }


}
