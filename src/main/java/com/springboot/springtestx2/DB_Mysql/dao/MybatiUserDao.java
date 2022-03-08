package com.springboot.springtestx2.DB_Mysql.dao;

import com.springboot.springtestx2.DB_Mysql.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface MybatiUserDao {
    User getUser(Long id);//方法名与mapper的id保持一致
}

/*Data Access Object DAO层*/