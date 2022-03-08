package com.springboot.springtestx2.DB_Mysql.Service.impl;

import com.springboot.springtestx2.DB_Mysql.Service.UserService;
import com.springboot.springtestx2.DB_Mysql.dao.MybatiUserDao;
import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final MybatiUserDao userDao;
    @Autowired
    public UserServiceImpl(MybatiUserDao userDao) {
        this.userDao = userDao;
    }
    /*
    * 1. 依赖不可变 final保证MybatiUserDao完成实例化后就不再改变
    * 2. 依赖不为空和依赖完全初始化 使用字段注入时只有用到才报错
    * */

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }
}
