package com.example.hotel.controller.room;

import com.example.hotel.bl.room.RoomService;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @PostMapping("/addroom")
    public ResponseVO addRoomInfo(@RequestBody Room hotelRoom) {
        roomService.addRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }
    @GetMapping("/{hotelId}/roomlist")
    public ResponseVO getRoomList(@PathVariable int hotelId){
        return ResponseVO.buildSuccess(roomService.getRoomList(hotelId));
    }

    @GetMapping("{hotelId}/getActiveRoomType")
    public ResponseVO getActiveRoomType(@PathVariable int hotelId){
        return ResponseVO.buildSuccess(roomService.getActiveRoomType(hotelId));
    }

    @GetMapping("{roomListId}/deleteRoom")
    public ResponseVO deleteRoom(@PathVariable int roomListId){
        return roomService.deleteRoom(roomListId);
    }

    @GetMapping("{roomId}/{num}/updateRoomNum")
    public ResponseVO updateRoomNum(@PathVariable int roomId, @PathVariable int num){
        return roomService.updateRoomNum(roomId,num);
    }

    @GetMapping("{roomId}/{num}/deleteRooms")
    public ResponseVO deleteRooms(@PathVariable int roomId,@PathVariable int num){
        return roomService.deleteRooms(roomId,num);
    }
}
