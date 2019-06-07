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

    public boolean isEmpty() {
        if (this.userName.equals("") || this.userName.isEmpty()) {
            return true;
        }
        if (this.status != 1) {
            return true;
        }
        return false;
    }
}
