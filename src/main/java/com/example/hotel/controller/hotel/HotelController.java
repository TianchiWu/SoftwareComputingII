package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.entity.*;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.entity.HotelSearchVO;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;
    @Autowired
    HotelRepository hotelRepository;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody Hotel hotel)  {
        return hotelService.addHotel(hotel);
    }
    @GetMapping("/{hotelId}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable int hotelId){
        return hotelService.deleteHotel(hotelId);
    }
    @PostMapping("/hotelSearch/search")
    public ResponseVO retrieveAllHotels(@RequestBody HotelSearchVO hotelSearchVO){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels(hotelSearchVO));
    }

    @GetMapping("{userId}/getHotelsByManagerId")
    public ResponseVO getHotelsByManagerId(@PathVariable int userId){
        return ResponseVO.buildSuccess(hotelService.getHotelsByManagerId(userId));
    }


    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @GetMapping("{hotelid}/{managerId}/setManager")
    public ResponseVO setManager(@PathVariable int hotelid,@PathVariable int managerId){
        return hotelService.setManager(hotelid,managerId);
    }

    @PostMapping("/{id}/hotelInfo/update")
    public ResponseVO updateHotelInfo(@RequestBody Hotel hotel, @PathVariable int id){
        return hotelService.updateHotelInfo(id,hotel.getHotelName(),hotel.getDescription(),hotel.getAddress(),hotel.getHotelStar(),hotel.getPhoneNum(),hotel.getInformation());

    }

}
