package com.example.hotel.controller.enterprise;

import com.example.hotel.bl.enterprise.EnterpriseService;
import com.example.hotel.entity.Enterprise;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;
    @PostMapping("/getAllEnterprises")
    public ResponseVO getAllEnterprises(){
        return ResponseVO.buildSuccess(enterpriseService.getAllEnterprise());
    }

    @PostMapping("/addEnterprise")
    public ResponseVO addEnterprise(@RequestBody Enterprise enterprise){
        return enterpriseService.addEnterprise(enterprise);
    }

    @GetMapping("/{name}/deleteEnterprise")
    public ResponseVO deleteEnterprise(@PathVariable String name){
        return enterpriseService.deleteEnterprise(name);
    }
}
