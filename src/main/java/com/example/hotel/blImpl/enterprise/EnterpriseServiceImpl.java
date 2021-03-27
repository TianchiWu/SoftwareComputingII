package com.example.hotel.blImpl.enterprise;

import com.example.hotel.bl.enterprise.EnterpriseService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.Enterprise;
import com.example.hotel.repository.EnterpriseRepository;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final static String ENTERPRISE_EXIST = "企业已存在";
    private final static String ADD_ERROR = "添加失败";
    @Autowired
    UserService userService;
    @Autowired
    EnterpriseRepository enterpriseRepository;
    @Override
    public List<Enterprise> getAllEnterprise(){
        return enterpriseRepository.findAll();
    }
    @Override
    public ResponseVO addEnterprise(Enterprise enterprise){

        if(enterpriseRepository.findByName(enterprise.getName())==null){
            try{
                enterpriseRepository.save(enterprise);
            }catch(Exception e){
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ADD_ERROR);
            }
            return ResponseVO.buildSuccess(true);
        }
        return ResponseVO.buildFailure(ENTERPRISE_EXIST);
    }

    @Override
    public ResponseVO deleteEnterprise(String name){
        userService.DeleteEnterprisename(name);
        enterpriseRepository.deleteByName(name);
        return ResponseVO.buildSuccess(true);
    }
}
