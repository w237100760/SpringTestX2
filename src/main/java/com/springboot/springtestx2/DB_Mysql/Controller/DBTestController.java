package com.springboot.springtestx2.DB_Mysql.Controller;

import com.springboot.springtestx2.DB_Mysql.Enum.SexEnum;
import com.springboot.springtestx2.DB_Mysql.Service.UserService;
import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/DB")
public class DBTestController {
    private final UserService userService;
    @Autowired
    public DBTestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/Test")
    public User getUser(Long id){
        return userService.getUser(id);
    }

    @PostMapping("/Test1")
    public int insertUser(@RequestParam String userName, Integer sex, String note){
        User user = new User();
        user.setUserName(userName);
        user.setNote(note);
        user.setSex(sex == 1 ? SexEnum.MALE : SexEnum.FEMALE);
        return userService.insertUser(user);
    }
}
