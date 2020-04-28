package com.example.myserver.demo.controller.privateImportant;

import java.util.ArrayList;
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
@CrossOrigin(origins = "http://localhost:8040", allowCredentials = "true")
@RequestMapping("/privateCloudDriver")
public class PersonalCloudDriverController {

  @Resource
  PersonalCloudDriverManager personalCloudDriverManager;

  @RequestMapping("/getFileList")
  @ResponseBody
  public CommonResult<List<CloudDriverFile>> getFileList(HttpServletRequest req) {
    CommonResultBuilder<List<CloudDriverFile>> resultBuilder = new CommonResultBuilder<List<CloudDriverFile>>();

    HttpSession session = req.getSession();
    // 获取session中用户信息
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    // 获取所查询的文件列表所在的文件夹
    String rootRouter = personalCloudDriverManager.getFileSecret(session, req);

    CommonResult<List<CloudDriverFile>> res;
    List<CloudDriverFile> fileList = new ArrayList<>();

    // 如果没有文件夹则从用户根目录取
    if (rootRouter == null || rootRouter.isEmpty()) {
      fileList = personalCloudDriverManager.getFileList(user);
    } else {
      fileList = personalCloudDriverManager.getFileList(user, rootRouter);
    }
    if (fileList.size() > 0) {
      // 对文件目录进行脱敏：把真实路径存入session，浏览器只获取id编号，根据id编号从session中获取真实路径
      personalCloudDriverManager.secretFileInfo(session, fileList);
    }

    res = resultBuilder.Success(fileList);
    return res;
  }

  @RequestMapping("/getFile")
  @ResponseBody
  public CommonResult<List<CloudDriverFile>> getFile(HttpServletRequest req) {
    CommonResultBuilder<List<CloudDriverFile>> resultBuilder = new CommonResultBuilder<List<CloudDriverFile>>();
    HttpSession session = req.getSession();
    User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    if (!user.ifContainRole(2)) {
      return resultBuilder.Error(RES_ENUM.INSUFFICIENT_PRIVILEGES);
    }
    CommonResult<List<CloudDriverFile>> res = resultBuilder.Success();
    return res;
  }
}