package com.example.hotel.blImpl.user;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.*;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.entity.UserType;
import com.example.hotel.repository.*;
import com.example.hotel.entity.UserForm;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String LACK_CREDIT = "信用值不足";
    private final static String REGISTER_ERROR = "注册失败";
    private final static String ENTERPRISE_ERROR = "您所在单位未参与合作";
    @Autowired
    HotelService hotelService;
    @Autowired
    UserRepository userRepository;
    //以下仅用于初始化数据库
    @Autowired
    EnterpriseRepository enterpriseRepository;
    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomListRepository roomListRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CreditListRepository creditListRepository;
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public ResponseVO registerAccount(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            return ResponseVO.buildSuccess(true);
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }

    @Override
    public User login(UserForm userForm) {

        User user = userRepository.findByEmail(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public ResponseVO updateUserInfo(int id,  String username, String phonenumber) {
        try {
            User user=userRepository.getOne(id);
            user.setUserName(username);
            user.setPhoneNumber(phonenumber);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateAllUserInfo(int id,String email, String password, String userName,String phoneNumber,String birthday){
        try {
            User user=userRepository.getOne(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setUserName(userName);
            user.setPhoneNumber(phoneNumber);
            user.setBirthday(birthday);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateManagerInfo(int id,String email, String password ){
        try {
            User user=userRepository.getOne(id);
            user.setEmail(email);
            user.setPassword(password);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updatePassword(int id,String password){
        try{
            User user=userRepository.getOne(id);
            user.setPassword(password);
            userRepository.save(user);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateCredit(int id, double credit) {
        try{
            User user=userRepository.getOne(id);
            user.setCredit(credit);
            userRepository.save(user);
            updateMemberLevel(id,credit);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public Double getUserCredit(int userId){
        User user=userRepository.getOne(userId);
        return user.getCredit();
    }


    @Override
    public ResponseVO registerNormalVIP(int id){
        try{
            if(getUserInfo(id).getCredit()<120){
                return ResponseVO.buildFailure(LACK_CREDIT);
            }
            ChangeMenmberTypeAndLevel(id,"普通会员","VIP1");
            updateMemberLevel(id,getUserInfo(id).getCredit());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(REGISTER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }



    @Override
    public ResponseVO registerEnterpriseVIP(int id){
        try{
            ChangeMenmberTypeAndLevel(id,"企业会员",null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO updateUserEnterprise(int id,String enterprisename){
        try{
            if(enterpriseRepository.findByName(enterprisename)!=null){
                User user=userRepository.getOne(id);
                user.setEnterprisename(enterprisename);
                userRepository.save(user);
                registerEnterpriseVIP(id);
                return ResponseVO.buildSuccess(true);
            }
        }catch (Exception e){
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildFailure(ENTERPRISE_ERROR);
    }


    @Override
    public ResponseVO cancelCredit(int userId){
        ChangeMenmberTypeAndLevel(userId,null,null);
        return ResponseVO.buildSuccess(true);
    }


    @Override
    public void DeleteEnterprisename(String name){
        List<User> target=userRepository.findAllByEnterprisename(name);
        for (User user : target) {
            user.setMembertype(null);
        }
        userRepository.saveAll(target);
    }

    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.HotelManager);
        if(userRepository.findByEmail(user.getEmail())==null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            return ResponseVO.buildSuccess(true);
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }

    @Override
    public ResponseVO addSale(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.Sale);
        if(userRepository.findByEmail(user.getEmail())==null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            return ResponseVO.buildSuccess(true);
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }

    @Override
    public ResponseVO addClient(User user){
        user.setUserType(UserType.Client);
        user.setMembertype(null);
        user.setMemberlevel(null);
        user.setEnterprisename(null);
        if(userRepository.findByEmail(user.getEmail())==null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            return ResponseVO.buildSuccess(true);
        }
        else{
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }
    @Override
    public List<User> getAllManagers() {
        return userRepository.findAllByUserType(UserType.HotelManager);
    }

    @Override
    public List<User> getAllClients(){
        return userRepository.findAllByUserType(UserType.Client);
    }

    @Override
    public List<User> getAllSales() {
        return userRepository.findAllByUserType(UserType.Sale);
    }
    @Override
    public ResponseVO deleteUser(int id){
        hotelService.deleteHotelManager(id);
        userRepository.deleteById(id);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public boolean isHotelManager(int id){
        if(id==0){
            return true;
        }
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get().getUserType().equals(UserType.HotelManager);
        }
        return false;
    }

    @Override
    public String userBirthday(int id){
        return userRepository.getOne(id).getBirthday();
    }

    /**
     * 升级会员等级
     * @param id
     * @param credit
     */
    private void updateMemberLevel(int id,double credit) {
        User user = this.getUserInfo(id);
        String memberLevel=null;
        if (user.getMembertype().equals("普通会员")) {
            if (credit < 120) {
                user.setMembertype(null);
                user.setMemberlevel(null);
            }
            else if (credit >= 120 && credit < 150) memberLevel="VIP1";
            else if (credit >= 150 && credit < 180) memberLevel="VIP2";
            else if (credit >= 180 && credit < 210) memberLevel="VIP3";
            else if (credit >= 210 && credit <= 250) memberLevel="VIP4";
            else memberLevel="VIP5";
            user.setMemberlevel(memberLevel);
            ChangeMenmberTypeAndLevel(id,user.getMembertype(),user.getMemberlevel());
        }
    }

    /**
     * 更改会员的会员类型和等级
     * @param id
     * @param memberType
     * @param memberLevel
     */
    private void ChangeMenmberTypeAndLevel(int id,String memberType,String memberLevel){
        User user=userRepository.getOne(id);
        user.setMembertype(memberType);
        user.setMemberlevel(memberLevel);
        userRepository.save(user);
    }
    @Override
    public ResponseVO initialize(){
        commentRepository.deleteAll();
        couponRepository.deleteAll();
        creditListRepository.deleteAll();
        enterpriseRepository.deleteAll();
        hotelRepository.deleteAll();
        orderListRepository.deleteAll();
        roomListRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        //设置企业
        Enterprise enterprise=new Enterprise();
        enterprise.setName("华为有限公司");
        enterpriseRepository.save(enterprise);
        //设置网站管理员
        User manager=new User();
        manager.setUserType(UserType.Manager);
        manager.setEmail("444@qq.com");
        manager.setPassword("123456");
        userRepository.save(manager);
        //新建酒店管理员
        User hotelmanager=new User();
        hotelmanager.setUserType(UserType.HotelManager);
        hotelmanager.setEmail("333@qq.com");
        hotelmanager.setPassword("123456");
        userRepository.save(hotelmanager);
        //新建测试用户
        User user1=new User();
        user1.setUserType(UserType.Client);
        user1.setEmail("111@qq.com");
        user1.setPassword("123456");
        user1.setUserName("测试一号");
        user1.setPhoneNumber("12345678919");
        user1.setCredit(120.0);
        user1.setBirthday("1999-02-21");
        user1.setMembertype("普通会员");
        user1.setMemberlevel("VIP1");
        user1.setEnterprisename(null);
        userRepository.save(user1);
        User user2=new User();
        user2.setUserType(UserType.Client);
        user2.setEmail("222@qq.com");
        user2.setPassword("123456");
        user2.setUserName("测试二号");
        user2.setPhoneNumber("12345678911");
        user2.setCredit(100.0);
        user2.setBirthday("1999-06-07");
        user2.setMembertype("企业会员");
        user2.setMemberlevel(null);
        user2.setEnterprisename("华为有限公司");
        userRepository.save(user2);
        //新建初始酒店
        Hotel hotel=new Hotel();
        hotel.setHotelName("酒店一号");
        hotel.setAddress("一号市一号路一号");
        hotel.setPhoneNum("11111111111");
        hotel.setDescription("这是酒店一号的简介");
        hotel.setHotelStar("五星级");
        hotel.setInformation("这是酒店一号的详情");
        hotel.setManagerId(0);
        hotelRepository.save(hotel);
        //为酒店设置管理员
        hotel=hotelRepository.findAllByManagerId(0).get(0);
        hotel.setManagerId(userRepository.findByEmail("333@qq.com").getId());
        hotelRepository.save(hotel);
        //太烦了酒店初始房间就不创建了
        return ResponseVO.buildSuccess(true);
    }

}
