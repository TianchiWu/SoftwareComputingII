package com.example.hotel.bl.credit;
import com.example.hotel.entity.CreditList;
import java.util.List;
public interface CreditService {

    /**
     * 创建信用变更记录
     * @param userid
     * @param orderid
     * @param change
     * @param reason
     */
    void CreditChangeDetal(int userid,int orderid,double change,String reason);

    /**
     * 获取信用变更记录
     * @param id
     * @return
     */
    List<CreditList> getCreditListById(int id);



}
