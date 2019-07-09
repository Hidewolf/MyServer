package com.example.myserver.demo.controller.privateImportant;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.myserver.demo.manager.PersonalCloudDriverManager;
import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder.RES_ENUM;
import com.example.myserver.demo.staticClass.PARAMS_KEY;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/privateCloudDriver")
public class PersonalCloudDriverController {

  @Resource
  PersonalCloudDriverManager personalCloudDriverManager;

  @RequestMapping("/getFileList")
  @ResponseBody
  public CommonResult<List<CloudDriverFile>> getFileList(HttpServletRequest req) {
    CommonResultBuilder<List<CloudDriverFile>> resultBuilder = new CommonResultBuilder();

    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    String rootRouter = req.getParameter("rootRouter");
    CommonResult<List<CloudDriverFile>> res;
    if(rootRouter==null || rootRouter.isEmpty()){
      res = resultBuilder.Success(personalCloudDriverManager.getFileList(user));
    }else{
      res = resultBuilder.Success(personalCloudDriverManager.getFileList(user, rootRouter));
    }
    return res;
  }

  @RequestMapping("/getFile")
  @ResponseBody
  public CommonResult<List<CloudDriverFile>> getFile(HttpServletRequest req) {
    CommonResultBuilder<List<CloudDriverFile>> resultBuilder = new CommonResultBuilder();
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    CommonResult res = resultBuilder.Success();
    return res;
  }
}