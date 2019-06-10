package com.example.myserver.demo.controller.privateImportant;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.staticClass.PARAMS_KEY;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privateCloudDriver")
public class PersonalCloudDriverController {

  @RequestMapping("/getFileList")
  @ResponseBody
  public List<CloudDriverFile> getFileList(HttpServletRequest req){
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if(user.ifContainRole(1)){}
    return null;
  }
}