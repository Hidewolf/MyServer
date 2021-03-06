package com.example.myserver.demo.controller;

import com.example.myserver.demo.manager.LogCheckManager;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder.RES_ENUM;
import com.example.myserver.demo.staticClass.PARAMS_KEY;
import com.example.myserver.demo.staticClass.RESULT_CONTENT;
import com.example.myserver.demo.staticClass.VALUE_CONTENT;
import com.example.myserver.demo.util.sysUtil;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins="http://192.168.7.194:8040",allowCredentials="true")
@RequestMapping("/login")
public class UserLoginController {
  @Resource
  LogCheckManager logCheckManager;

  // 登录
  @PostMapping("/log")
  @ResponseBody
  public CommonResult<User> login(HttpServletRequest req) {
    HttpSession session = req.getSession();
    CommonResultBuilder<User> resultBuilder = new CommonResultBuilder<User>();
    String userName = req.getParameter(PARAMS_KEY.USER_NAME);
    if (userName == null || userName.equals("") || req.getParameter(PARAMS_KEY.PASS_WORD) == null
        || req.getParameter(PARAMS_KEY.PASS_WORD).toString().equals("")) {
      return resultBuilder.Error(RES_ENUM.LOST_NECESSARY_IMFORMATION);
    }
    // md5加密
    String psw = sysUtil.md5(req.getParameter(PARAMS_KEY.PASS_WORD).toString());
    // 验证密码
    if (!logCheckManager.checkLog(userName, psw)) {
      return resultBuilder.Error(RES_ENUM.NOT_CURRENT_INFO);
    }
    // 通过uuid验证登录状态，防止多处登录（等集成redis后实现）
    String uuid = UUID.randomUUID().toString().replace("-", "");
    User user = logCheckManager.getUserInfo(userName);
    // USERS.put(String.valueOf(user.getId()), uuid);
    session.setAttribute(PARAMS_KEY.USER_ID, user.getId());
    session.setAttribute(PARAMS_KEY.USER_INFO, user);
    session.setAttribute(PARAMS_KEY.SESSION_UUID, uuid);
    session.setAttribute(PARAMS_KEY.LOG_TYPE, VALUE_CONTENT.HOME_MANAGER_USER);
    return resultBuilder.Success(user);
  }

  // 注册
  @RequestMapping("/regist")
  @ResponseBody
  public Map<String, Object> regist(HttpServletRequest req) {
    Map<String, Object> res = new HashMap<String, Object>();
    // 获取用户名
    String userName = req.getParameter(PARAMS_KEY.USER_NAME);
    // 验证用户名是否重复
    if (logCheckManager.checkUserExists(userName)) {
      res.put(RESULT_CONTENT.ERRER_CODE_KEY, RESULT_CONTENT.REPEAT_CONTENT_CODE);
      res.put(RESULT_CONTENT.MESSAGE_KEY, RESULT_CONTENT.REPEAT_CONTENT_MSG);
      return res;
    }
    // 获取密码（md5加密）
    String psw = sysUtil.md5(req.getParameter(PARAMS_KEY.PASS_WORD));
    // 存入用户
    logCheckManager.addUser(userName, psw);
    return null;
  }

}
