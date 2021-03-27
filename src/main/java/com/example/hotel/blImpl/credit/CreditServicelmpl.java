package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.CreditList;
import com.example.hotel.repository.CreditListRepository;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CreditServicelmpl implements CreditService {
    private final static String UPDATE_ERROR = "修改失败";
    @Autowired
    UserService userService;
    @Autowired
    CreditListRepository creditListRepository;
    @Override
    public List<CreditList> getCreditListById(int id){
        return creditListRepository.findAllByUserId(id);
    }

    @Override
    public void CreditChangeDetal(int userid,int orderid,double change,String reason){
        double creditBe=userService.getUserCredit(userid);
        double creditAf=creditBe+change;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        CreditList credit=new CreditList();
        credit.setUserId(userid);
        if(orderid!=0){
            credit.setOrderId(orderid);
        }
        credit.setCurDate(curdate);
        credit.setAction(reason);
        credit.setCreditBefore(creditBe);
        credit.setCreditAfter(creditAf);
        addCreditRecord(credit);
        userService.updateCredit(userid,creditAf);
    }

    /**
     * 新增一条信用记录
     * @param credit
     */
    private void addCreditRecord(CreditList credit){
        try {
            creditListRepository.save(credit);
        }catch (Exception e){
            System.out.println(e.getMessage());
            ResponseVO.buildFailure(UPDATE_ERROR);
            return;
        }
        ResponseVO.buildSuccess(true);
    }
}
