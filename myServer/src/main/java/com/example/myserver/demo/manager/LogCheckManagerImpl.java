package com.example.myserver.demo.manager;

import com.example.myserver.demo.model.User;
import com.example.myserver.demo.service.UserInfoService;
import com.example.myserver.demo.staticClass.PARAMS_KEY;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("logCheckManager")
public class LogCheckManagerImpl implements LogCheckManager {
    @Resource
    UserInfoService userInfoService;
    private Map<String, String> USER = new HashMap<String, String>();

    {
        USER.put("hideWolf", "12362448Peter");
    }

    @Override
    public boolean checkLog(String userName, String psw) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        params.put("psw", psw);
        List<User> users = userInfoService.selectUser(params);
        return users.size() == 1;
    }

    @Override
    public User getUserInfo(String userName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        List<User> users = userInfoService.selectUser(params);
        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public boolean checkUserExists(String userName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        List<User> users = userInfoService.selectUser(params);
        return users.size() == 1;
    }

    @Override
    public boolean addUser(String userName, String psw) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put(PARAMS_KEY.USER_NAME,userName);
        params.put(PARAMS_KEY.PASS_WORD,psw);
        return userInfoService.insertUser(params);
    }
}
