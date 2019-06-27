package com.example.myserver.demo.controller.privateImportant;

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

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privateCloudDriver")
public class PersonalCloudDriverController {

  @Resource
  PersonalCloudDriverManager personalCloudDriverManager;

  @Resource(name = "resultBuilder")
  CommonResultBuilder resultBuilder;

  @RequestMapping("/getFileList")
  @ResponseBody
  public CommonResult getFileList(HttpServletRequest req) {
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    String rootRouter = req.getParameter("rootRouter");
    CommonResult<CloudDriverFile> res;
    if(rootRouter==null || rootRouter.isEmpty()){
      res = resultBuilder.Success(personalCloudDriverManager.getFileList(user));
    }else{
      res = resultBuilder.Success(personalCloudDriverManager.getFileList(user, rootRouter));
    }
    return res;
  }

  @RequestMapping("/getFile")
  @ResponseBody
  public CommonResult getFile(HttpServletRequest req) {
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    CommonResult<CloudDriverFile> res = resultBuilder.Success();
    return res;
  }
}