package com.example.myserver.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder.RES_ENUM;
import com.example.myserver.demo.staticClass.PARAMS_KEY;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  @CrossOrigin
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (user == null || user.isEmpty()) {

      CommonResultBuilder resultBuilder = new CommonResultBuilder<>();

      if (request.getHeader("Origin") != null) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      }

      // 重定向到登陆界面
      PrintWriter writer = null;

      writer = response.getWriter();
      writer.print(JSONObject.toJSONString(resultBuilder.Error(RES_ENUM.NO_LOG)));
      if (writer != null)
        writer.close();

      return false;
    }
    return true;
  }

}
