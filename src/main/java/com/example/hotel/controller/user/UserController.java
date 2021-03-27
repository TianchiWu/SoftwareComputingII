package com.example.hotel.controller.user;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.user.UserService;
import com.example.hotel.entity.User;
import com.example.hotel.entity.UserForm;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/user")
public class UserController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user = userService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);

    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody User user) {
        return userService.registerAccount(user);
    }


    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = userService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody User user,@PathVariable int id){
        return userService.updateUserInfo(id,user.getUserName(),user.getPhoneNumber());

    }

    @PostMapping("/{id}/userInfo/password")
    public ResponseVO updatePassword(@RequestBody User user,@PathVariable int id){
        return userService.updatePassword(id,user.getPassword());
    }
    //展示信用变更记录


    @PostMapping("/{id}/registerNormal")
    public ResponseVO registerNormalVIP(@PathVariable int id){
        return userService.registerNormalVIP(id);
    }

    @PostMapping("/{id}/registerEnterprise")
    public ResponseVO registerEnterpriseVIP(@PathVariable int id){
        return userService.registerEnterpriseVIP(id);
    }

    @PostMapping("/userInfo/enterprisename")
    public ResponseVO updateEnterprise(@RequestBody User user){
        return userService.updateUserEnterprise(user.getId(),user.getEnterprisename());
    }
    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(userService.getAllManagers());
    }

    @PostMapping("/getAllClients")
    public ResponseVO getAllClients() { return ResponseVO.buildSuccess(userService.getAllClients()); }

    @PostMapping("/getAllSales")
    public ResponseVO getAllSales(){
        return ResponseVO.buildSuccess(userService.getAllSales());
    }

    @GetMapping("/{id}/deleteManager")
    public ResponseVO deleteManager(@PathVariable int id){

        return ResponseVO.buildSuccess(userService.deleteUser(id));
    }

    @GetMapping("/{id}/deleteClient")
    public ResponseVO deleteClient(@PathVariable int id){
        return ResponseVO.buildSuccess(userService.deleteUser(id));
    }

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){

        return userService.addManager(userForm);
    }
    @PostMapping("/addSale")
    public ResponseVO addSale(@RequestBody UserForm userForm){

        return userService.addSale(userForm);
    }

    @PostMapping("/addClient")
    public ResponseVO addClient(@RequestBody User user){
        return userService.addClient(user);
    }

    @PostMapping("/{id}/userInfo/managerupdate")
    public ResponseVO updateClientInfo(@RequestBody User user, @PathVariable int id){
        return userService.updateAllUserInfo(id,user.getEmail(),user.getPassword(),user.getUserName(),user.getPhoneNumber(),user.getBirthday());

    }

    @PostMapping("/{id}/managerInfo/update")
    public ResponseVO updateManagerInfo(@RequestBody User user, @PathVariable int id){
        return userService.updateManagerInfo(id,user.getEmail(),user.getPassword());

    }
    @GetMapping("initialize")
    public ResponseVO initialize(){
        return ResponseVO.buildSuccess(userService.initialize());
    }
    @PostMapping("{userId}/cancelCredit")
    public ResponseVO cancelCredit(@PathVariable int userId){
        return userService.cancelCredit(userId);
    }
}
