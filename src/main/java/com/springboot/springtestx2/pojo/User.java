package com.springboot.springtestx2.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String note;

    public User(Long id, String userName, String note) {
        this.id = id;
        this.userName = userName;
        this.note = note;
    }
}
