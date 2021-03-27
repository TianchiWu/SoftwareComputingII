package com.example.hotel.controller.credit;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/credit")
public class CreditController {
    @Autowired
    CreditService creditService;


    @GetMapping("/{id}/{credit}/addCredit")
    public ResponseVO addCredit(@PathVariable int id,@PathVariable double credit){
        creditService.CreditChangeDetal(id,0,credit,"信用充值");
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{id}/getCreditList")
    public ResponseVO retrieveUserCredit(@PathVariable int id ){
        return ResponseVO.buildSuccess(creditService.getCreditListById(id));
    }
}

