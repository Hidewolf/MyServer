package com.example.myserver.demo.service;

import com.example.myserver.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    List<User> selectUser(Map<String,Object> params);

    boolean insertUser(Map<String, Object> params);
}
