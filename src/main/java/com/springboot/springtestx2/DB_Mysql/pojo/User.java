package com.springboot.springtestx2.DB_Mysql.pojo;

import com.springboot.springtestx2.DB_Mysql.Enum.SexEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("user")//resultType="user" 或者使用全限定名com.springboot.springtestx2.DB_Mysql.pojo.User
@Data
public class User {
    private Long id;
    private String userName;
    private String note;
    private SexEnum sex;
}
