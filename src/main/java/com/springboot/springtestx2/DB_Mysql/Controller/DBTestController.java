package com.springboot.springtestx2.DB_Mysql.Controller;

import com.springboot.springtestx2.DB_Mysql.Service.UserService;
import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DB")
public class DBTestController {
    @Autowired
    public UserService userService;

    @GetMapping("/Test")
    public User test(Long id){
        return userService.getUser(id);
    }
}
