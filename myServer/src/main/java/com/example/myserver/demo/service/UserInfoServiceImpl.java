package com.example.myserver.demo.service;

import com.example.myserver.demo.mapper.UserInfoMapper;
import com.example.myserver.demo.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;

    public List<User> selectUser(Map<String, Object> params) {
        return userInfoMapper.selectUser(params);
    }

    @Override
    public boolean insertUser(Map<String, Object> params) {
        return userInfoMapper.insertUser(params);
    }
}
