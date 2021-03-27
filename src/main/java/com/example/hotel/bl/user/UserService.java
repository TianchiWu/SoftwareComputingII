package com.example.hotel.bl.user;
import com.example.hotel.entity.ResponseVO;
import com.example.hotel.entity.User;
import com.example.hotel.entity.UserForm;

import java.util.List;


public interface UserService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(User user);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id,String username,String phonenumber);

    /**
     * 管理员修改用户信息
     * @param id
     * @param email
     * @param password
     * @param userName
     * @param phoneNumber
     * @param birthday
     * @return
     */
    ResponseVO updateAllUserInfo(int id,String email, String password, String userName,String phoneNumber,String birthday);

    /**
     * 管理员修改酒店管理员信息
     * @param id
     * @param email
     * @param password
     * @return
     */
    ResponseVO updateManagerInfo(int id,String email, String password);
    /**
     * 更新用户密码
     * @param id
     * @param password
     * @return
     */
    ResponseVO updatePassword(int id,String password);

    /**
     *
     * @param id
     * @param credit
     * @return
     */
    ResponseVO updateCredit(int id,double credit);

    /**
     * 获取用户的信用值
     * @param userId
     * @return
     */
    Double getUserCredit(int userId);

    /**
     * 注册普通会员
     * @param id
     * @return
     */
    ResponseVO registerNormalVIP(int id);

    /**
     * 注册企业会员
     * @param id
     * @return
     */
    ResponseVO registerEnterpriseVIP(int id);

    /**
     * 更新用户企业信息
     * @param id
     * @param enterprisename
     * @return
     */
    ResponseVO updateUserEnterprise(int id,String enterprisename);

    /**
     * 退出会员
     * @param userId
     * @return
     */
    ResponseVO cancelCredit(int userId);

    /**
     * 所有企业名相同的用户强制退出企业会员
     * @param name
     */
    void DeleteEnterprisename(String name);

    /**
     * 添加酒店管理人员账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);

    /**
     * 添加网站营销人员账号
     * @param userForm
     * @return
     */
    ResponseVO addSale(UserForm userForm);

    /**
     * 添加用户账号
     * @return
     */
    ResponseVO addClient(User user);
    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    /**
     * 获得所有用户信息
     * @return
     */
    List<User> getAllClients();

    /**
     * 获得所有酒店营销人员信息
     * @return
     */
    List<User> getAllSales();

    /**
     * 删除酒店管理员
     * @param id
     * @return
     */
    ResponseVO deleteUser(int id);

    /**
     * 判断是否为酒店管理员
     * @param id
     * @return
     */
    boolean isHotelManager(int id);

    /**
     * 获取用户生日信息
     * @param id
     * @return
     */
    String userBirthday(int id);
    /**
     * 仅测试时用 初始化数据
     * @return
     */
    ResponseVO initialize();
}
