package com.example.hotel.bl.enterprise;
import com.example.hotel.entity.Enterprise;
import com.example.hotel.entity.ResponseVO;
import java.util.List;

public interface EnterpriseService {

    /**
     * 添加合作企业
     * @param enterprise
     * @return
     */
    ResponseVO addEnterprise(Enterprise enterprise);

    /**
     * 删除合作企业
     * @param name
     * @return
     */
    ResponseVO deleteEnterprise(String name);

    /**
     * 获得所有合作企业信息
     * @return
     */
    List<Enterprise> getAllEnterprise();
}
