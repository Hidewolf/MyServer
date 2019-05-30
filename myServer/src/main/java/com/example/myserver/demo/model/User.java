package com.example.myserver.demo.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userName;
    private int status;
    private String remark;
    private int roleNo;
    private int roleLevel;
}
