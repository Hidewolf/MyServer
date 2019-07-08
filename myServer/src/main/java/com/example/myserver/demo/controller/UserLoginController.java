package com.example.myserver.demo.controller;

import com.example.myserver.demo.manager.LogCheckManager;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.staticClass.PARAMS_KEY;
import com.example.myserver.demo.staticClass.RESULT_CONTENT;
import com.example.myserver.demo.staticClass.VALUE_CONTENT;
import com.example.myserver.demo.util.sysUtil;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin
@RequestMapping("/login")
public class UserLoginController {
  @Resource
  LogCheckManager logCheckManager;

  // 登录
  @RequestMapping("/log")
  @ResponseBody
  public Map<String, Object> login(HttpServletRequest req, @RequestBody Map<String, Object> map) {
    HttpSession session = req.getSession();
    Map<String, Object> res = new HashMap();
    String userName = map.get(PARAMS_KEY.USER_NAME).toString();
    if (userName == null || userName.equals("") || map.get(PARAMS_KEY.PASS_WORD) == null
        || map.get(PARAMS_KEY.PASS_WORD).toString().equals("")) {
      res.put(RESULT_CONTENT.MESSAGE_KEY, RESULT_CONTENT.LOST_NECESSARY_IMFORMATION_MSG);
      res.put(RESULT_CONTENT.ERRER_CODE_KEY, RESULT_CONTENT.LOST_NECESSARY_IMFORMATION_CODE);
      return res;
    }
    // md5加密
    String psw = sysUtil.md5(map.get(PARAMS_KEY.PASS_WORD).toString());
    // 验证密码
    if (!logCheckManager.checkLog(userName, psw)) {
      res.put(RESULT_CONTENT.MESSAGE_KEY, RESULT_CONTENT.NOT_CURRENT_INFO_MSG);
      res.put(RESULT_CONTENT.ERRER_CODE_KEY, RESULT_CONTENT.NOT_CURRENT_INFO_CODE);
      return res;
    }
    // 通过uuid验证登录状态，防止多处登录（等集成redis后实现）
    String uuid = UUID.randomUUID().toString().replace("-", "");
    User user = logCheckManager.getUserInfo(userName);
    // USERS.put(String.valueOf(user.getId()), uuid);
    session.setAttribute(PARAMS_KEY.USER_ID, user.getId());
    session.setAttribute(PARAMS_KEY.USER_INFO, user);
    session.setAttribute(PARAMS_KEY.SESSION_UUID, uuid);
    session.setAttribute(PARAMS_KEY.LOG_TYPE, VALUE_CONTENT.HOME_MANAGER_USER);
    res.put(RESULT_CONTENT.MESSAGE_KEY, RESULT_CONTENT.SUCCESS_MSG);
    res.put(RESULT_CONTENT.ERRER_CODE_KEY, RESULT_CONTENT.SUCCESS_CODE);
    return res;
  }

  // 注册
  @RequestMapping("/regist")
  @ResponseBody
  public Map<String, Object> regist(HttpServletRequest req) {
    Map<String, Object> res = new HashMap();
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
