package com.example.hotel.blImpl.order;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.room.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.OrderList;
import com.example.hotel.repository.OrderListRepository;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String CREDIT_LACK="信用积分不足";
    @Autowired
    CreditService creditService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;

    @Autowired
    OrderListRepository orderListRepository;


    @Override
    public boolean isHotelHaveActiveOrder(int hotelId){
        List<OrderList> orders=getHotelOrders(hotelId);
        for (OrderList order : orders) {
            String state = order.getOrderState();
            if (state.equals("已预订") || state.equals("已入住") || state.equals("入住异常")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ResponseVO addOrder(OrderList orderList) {
        String checkInDate=orderList.getCheckInDate();
        String checkOutDate=orderList.getCheckOutDate();

        ArrayList<Integer>  availableNums = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //起始日期
            Date d1 = sdf.parse(checkInDate);
            //结束日期
            Date d2 = sdf.parse(checkOutDate);
            Date tmp;
            Calendar dd =Calendar.getInstance();
            dd.setTime(d1);
            tmp=dd.getTime();
            while(tmp.getTime()<=d2.getTime()) {
                availableNums.add(this.getAvailableRoomNumByDate(sdf.format(tmp),orderList.getRoomId()));
                dd.add(Calendar.DAY_OF_MONTH, 1);
                tmp=dd.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Integer availableNum : availableNums) {
            if (availableNum <= 0) {
                return ResponseVO.buildFailure(ROOMNUM_LACK);
            }
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderList.setCreateDate(curdate);
            orderList.setOrderState("已预订");
            if(userService.getUserCredit(orderList.getUserId())<=0)
                return ResponseVO.buildFailure(CREDIT_LACK);
            orderList.setCheckInDate(orderList.getCheckInDate()+" 12:00");
            orderList.setCheckOutDate(orderList.getCheckOutDate()+" 12:00");
            orderList.setBirthday(userService.userBirthday(orderList.getUserId()));
            orderList.setLast(orderList.getRoomNum());
            orderListRepository.save(orderList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }



    @Override
    public List<OrderList> getOrdersByHotelId(int hotelId){
        checkOrder();
        return orderListRepository.findAllByHotelId(hotelId);
    }

    @Override
    public List<OrderList> getAbnormalOrders() {
        checkOrder();
        List<OrderList> result=new ArrayList<>();
        List<OrderList> target;
        target=orderListRepository.findAllByOrderState("退房异常");
        result.addAll(target);
        target=orderListRepository.findAllByOrderState("入住异常");
        result.addAll(target);
        target=orderListRepository.findAllByOrderState("已撤销");
        result.addAll(target);
        return result;
    }

    @Override
    public List<OrderList> getUserOrders(int userid) {
        checkOrder();
        return orderListRepository.findAllByUserId(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String annulDate = sf.format(date);
        OrderList order=getOrderById(orderid);
        double price=order.getPrice();
        int userId=order.getUserId();
        String latestTime=order.getCheckInDate().substring(0,10)+" 00:00";//最迟撤销时间checkInDate0点
        //扣信用分
        if(order.getOrderState().equals("已预订")&&timeCompare(annulDate,latestTime)>=0){
            creditService.CreditChangeDetal(userId,orderid,-price/20,"撤销订单");
        }
        else if(order.getOrderState().equals("已预订")){

        }
        else {
            creditService.CreditChangeDetal(userId,orderid,price/20,"网站营销人员撤销异常订单");
        }
        OrderList orderlist=orderListRepository.getOne(orderid);
        int addNum=orderlist.getRoomNum();
        int roomId = orderlist.getRoomId();
        int changeNum=addNum-orderlist.getLast();
        roomService.updateRoomCurNum(roomId,changeNum);
        orderlist.setAnnulDate(annulDate);
        orderlist.setOrderState("已撤销");
        orderListRepository.save(orderlist);
        roomService.updateRoomListState(orderid,"空房");
        checkOrder();
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public boolean  hasUserHotelOrders(int hotelId,int userId){

        List<OrderList> orderLists=orderListRepository.findAllByHotelIdAndUserIdAndOrderState(hotelId,userId,"已退房");
        return orderLists.size() > 0;
    }

    @Override
    public List<OrderList> getHotelOrders(Integer hotelId) {
        checkOrder();
        List<OrderList> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public OrderList getOrderById(int orderid) {
        return orderListRepository.getOne(orderid);
    }

    @Override
    public ResponseVO checkin(int orderid) {
        //完成订单逻辑的具体实现
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String checkInDate = sf.format(date);
        OrderList order=getOrderById(orderid);
        if(timeCompare(checkInDate,order.getCheckInDate())<0){
            return ResponseVO.buildFailure("未到入住时间");
        }
        if(order.getOrderState().equals("入住异常")) {
            creditService.CreditChangeDetal(order.getUserId(), orderid, order.getPrice() / 20, "酒店管理员补执行订单");
        }
        roomService.updateRoomListState(orderid,"已入住");
        order.setOrderState("已入住");
        order.setCheckInDate(checkInDate);
        orderListRepository.save(order);
        return ResponseVO.buildSuccess(true);

    }


    @Override
    public ResponseVO checkout(int orderid) {
        //完成订单逻辑的具体实现

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String checkOutDate = sf.format(date);
        roomService.updateRoomListState(orderid,"空房");
        OrderList orderList=orderListRepository.getOne(orderid);
        roomService.updateRoomCurNum(orderList.getRoomId(),orderList.getRoomNum());
        orderList.setOrderState("已退房");
        orderList.setCheckOutDate(checkOutDate);
        orderListRepository.save(orderList);
        checkOrder();
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public List<Integer> getHotelIdByUserIdAndOrderState(int userId,String orderstate){
        List<OrderList> target;
        if(!orderstate.equals("")) {
             target = orderListRepository.findAllByUserIdAndOrderState(userId, orderstate);
        }
        else{
            target = orderListRepository.findAllByUserId(userId);
        }
        List<Integer> result=new ArrayList<>();
        for (OrderList orderList : target) {
            if(!result.contains(orderList.getHotelId())){
                result.add(orderList.getHotelId());
            }
        }
        return result;
    }
    @Override
    public void checkOrder(){
        checkAbnormal();
        matchOrder();
    }

    /**
     * 获得所有的订单
     * @return
     */
    private List<OrderList> getAllOrders() {
        return orderListRepository.findAll();
    }

    /**
     * 检查订单异常
     */
    private void checkAbnormal(){

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        List<OrderList> allOrders=orderListRepository.findAll();
        for (OrderList order : allOrders) {
            String LastcheckInTime = order.getCheckInDate().substring(0, 10) + " 23:59";
            if (timeCompare(curdate, LastcheckInTime)>=0 && (order.getOrderState().equals("已预订"))) {
                creditService.CreditChangeDetal(order.getUserId(), order.getId(), -order.getPrice() / 20, "入住异常");
                order.setOrderState("入住异常");
                orderListRepository.save(order);

            }
        }
        allOrders=orderListRepository.findAll();
        for (OrderList order : allOrders) {
            String LastcheckOutTime = order.getCheckOutDate().substring(0, 10) + " 12:00";
            if (timeCompare(curdate, LastcheckOutTime)>=0 && ((order.getOrderState().equals("已入住") || order.getOrderState().equals("入住异常")))) {
                if (order.getOrderState().equals("已入住")) {
                    creditService.CreditChangeDetal(order.getUserId(), order.getId(), -order.getPrice() / 20, "退房异常");
                }
                this.checkout(order.getId());
                order.setOrderState("退房异常");
                orderListRepository.save(order);

            }
        }

        ResponseVO.buildSuccess(true);
    }

    /**
     * 为没有匹配过的订单匹配房间
     */
    private void matchOrder(){
        List<OrderList> orders=orderListRepository.findAll();
        for(int l=0;l<orders.size();l++) {

            for(int i=0;i< orders.size();i++){
                if(orders.get(i).getLast()==0){
                    orders.remove(i);
                    i--;
                }
            }

            for (OrderList order : orders) {
                for (int j = 0; j < order.getLast(); j++) {
                    Integer roomListId = roomService.getActiveRoom(order.getRoomId());
                    if (roomListId == null) {
                        ArrayList<OrderList> oldOrders = new ArrayList<>();
                        for (Integer oldOrderId : roomService.getOldOrderIds(order.getRoomId())) {
                            oldOrders.add(orderListRepository.getOne(oldOrderId));
                        }
                        for (int k = 0; k < oldOrders.size(); k++) {
                            if (timeCompare(order.getCheckInDate(), oldOrders.get(k).getCheckInDate())>=0) {
                                oldOrders.remove(k);
                                k--;
                            }
                        }
                        if (oldOrders.size() != 0) {
                            int orderId = oldOrders.get(0).getId();
                            OrderList orderList=orderListRepository.getOne(orderId);
                            orderList.setLast(orderList.getLast()+1);
                            orderListRepository.save(orderList);
                            roomListId = roomService.getFirstByOrderId(orderId);
                            roomService.updateRoomInfo(order.getRoomId(), -1);
                        } else {
                            break;
                        }
                    }
                    roomService.matchRoom(roomListId,order.getId());
                    order.setLast(order.getLast()-1);
                    orderListRepository.save(order);
                    roomService.updateRoomInfo(order.getRoomId(), 1);
                }

            }
            orders=orderListRepository.findAll();
        }
    }

    /**
     * 获取某一天某一种房间剩余的房间数
     * @param date
     * @param roomId
     * @return
     */
    private int getAvailableRoomNumByDate(String date,int roomId){
        List<OrderList> RoomOrders=orderListRepository.findAllByRoomIdAndOrderState(roomId,"已预订");
        List<OrderList> orderLists=orderListRepository.findAllByRoomIdAndOrderState(roomId,"已入住");
        RoomOrders.addAll(orderLists);
        orderLists=orderListRepository.findAllByRoomIdAndOrderState(roomId,"入住异常");
        RoomOrders.addAll(orderLists);
        int total=roomService.getRoomTotal(roomId);

        for (OrderList target : RoomOrders) {
            if (dateCompare(date, target.getCheckInDate())>=0 && (dateCompare(date, target.getCheckOutDate())<0)) {
                total -= target.getRoomNum();
            }
        }
        return total;
    }

    /**
     * 比较日期
     * @param t1
     * @param t2
     * @return
     */
    private int dateCompare(String t1,String t2){
        try {
            Date a = new SimpleDateFormat("yyyy-MM-dd").parse(t1);
            Date b = new SimpleDateFormat("yyyy-MM-dd").parse(t2);
            return a.compareTo(b);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * 比较时间
     * @param t1
     * @param t2
     * @return
     */
    private Integer timeCompare(String t1,String t2){
        try {
            Date a = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(t1);
            Date b = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(t2);
            return a.compareTo(b);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
