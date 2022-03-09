package com.springboot.springtestx2.DB_Mysql.Service.impl;

import com.springboot.springtestx2.DB_Mysql.Service.UserService;
import com.springboot.springtestx2.DB_Mysql.dao.MybatiUserDao;
import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int insertUser(User user) {
        userDao.insertUser(user);
        return user.getId().intValue();//mybatis返回自增id 填入user
    }
}

/*@Transactional 不能在同一个类中调用
* 所有类自身调用都不会通过Aop生成代理对象当然也无法增强
* */