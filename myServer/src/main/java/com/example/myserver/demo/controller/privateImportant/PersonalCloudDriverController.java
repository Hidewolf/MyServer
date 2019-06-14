package com.example.myserver.demo.controller.privateImportant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.myserver.demo.manager.PersonalCloudDriverManager;
import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.staticClass.PARAMS_KEY;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privateCloudDriver")
public class PersonalCloudDriverController {

  @Resource
  PersonalCloudDriverManager personalCloudDriverManager;

  @RequestMapping("/getFileList")
  @ResponseBody
  public CommonResult getFileList(HttpServletRequest req){
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if(!user.ifContainRole(2)){
      return null;
    }
    return null;
  }
}