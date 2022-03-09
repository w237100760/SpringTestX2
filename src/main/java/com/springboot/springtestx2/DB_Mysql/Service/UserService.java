package com.springboot.springtestx2.DB_Mysql.Service;

import com.springboot.springtestx2.DB_Mysql.pojo.User;

public interface UserService {
    User getUser(Long id);
    int insertUser(User user);
}
