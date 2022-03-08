package com.springboot.springtestx2.DB_Mysql.Service.impl;

import com.springboot.springtestx2.DB_Mysql.Service.UserService;
import com.springboot.springtestx2.DB_Mysql.dao.MybatiUserDao;
import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MybatiUserDao userDao;

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }
}
