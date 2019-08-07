package com.example.myserver.demo.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder.RES_ENUM;
import com.example.myserver.demo.service.PersonalCloudDriverService;

import org.springframework.stereotype.Service;

@Service("personalCloudDriverManager")
public class PersonalCloudDriverManagerImpl implements PersonalCloudDriverManager {

  final private String driverRouter = "D:\\Ts\\private\\";

  @Resource(name = "personalCloudDriverService")
  PersonalCloudDriverService personalCloudDriverService;

  @Override
  public List<CloudDriverFile> getFileList(User user, String rootRouter) {
    File fileRoot = new File(driverRouter + rootRouter);
    List<CloudDriverFile> res = new ArrayList();
    if (fileRoot.exists()) {
      File[] files = fileRoot.listFiles();
      for (int i = 0; i < files.length; i++) {
        res.add(new CloudDriverFile(files[i], user, i));
      }
    } else {
      System.out.println("file not exists");
    }
    return res;
  }

  @Override
  public List<CloudDriverFile> getFileList(User user) {
    User cfgInfo = this.getUserDir(user);
    String rootRouter;
    if (cfgInfo == null) {
      rootRouter = this.createRootDir(user, user.getUserName() + "defaultDir");
    } else {
      rootRouter = cfgInfo.getInfo().toString();
    }

    if (rootRouter == null || rootRouter.isEmpty()) {
      rootRouter = this.createRootDir(user, user.getUserName() + "defaultDir");
    }
    return this.getFileList(user, rootRouter);
  }

  private User getUserDir(User user) {
    User res = personalCloudDriverService.getCfgInfo(user);
    return res;
  }

  @Override
  public String createRootDir(User user, String dirName) {
    User cfgInfo = this.getUserDir(user);
    if (cfgInfo == null) {
      personalCloudDriverService.insertCfgInfo(user, dirName);
      CommonResult res = this.createDir(user, "", dirName);
      if (res.isSuccess()) {
        return dirName;
      } else {
        System.out.println("create '" + dirName + "' dir failed as :" + res.getErrInfo());
        return "createDir failed as :" + res.getErrInfo();
      }
    } else {
      return cfgInfo.getInfo().toString();
    }
  }

  @Override
  public CommonResult<String> createDir(User user, String rootDirName, String dirName) {
    CommonResultBuilder<String> resultBuilder = new CommonResultBuilder();
    if (rootDirName != null && !rootDirName.isEmpty()) {
      User cfgInfo = this.getUserDir(user);
      if (rootDirName.indexOf("\\" + cfgInfo.getInfo().toString() + "\\") < 0) {
        return resultBuilder.Error(RES_ENUM.NOT_CURRENT_INFO);
      }
    }

    if (dirName == null || dirName.isEmpty()) {
      return resultBuilder.Error(RES_ENUM.LOST_NECESSARY_IMFORMATION);
    } else if (dirName.indexOf(rootDirName) < 0) {
      dirName = rootDirName + dirName;
    }

    File dir = new File(driverRouter + dirName);
    boolean res = dir.mkdir();

    if (res) {
      return resultBuilder.Success(dirName);
    } else {
      return resultBuilder.Error(RES_ENUM.NOT_CURRENT_INFO, "Path incompleteness");
    }
  }

  @Override
  public void secretFileInfo(HttpSession session, List<CloudDriverFile> fileList) {
    session.setAttribute("fileList", fileList);
    for (int i = 0; i < fileList.size(); i++) {
      fileList.get(i).removeSecret();
    }
  }

  @Override
  public String getFileSecret(HttpSession session, HttpServletRequest req) {
    String rootRouter = req.getParameter("rootRouter");
    List<CloudDriverFile> fileList = (List<CloudDriverFile>) session.getAttribute("fileList");
    if(rootRouter!=null && fileList!=null &&fileList.size()>0){
      for(int i = 0;i<fileList.size();i++){
        if(fileList.get(i).getIndex() == rootRouter){
          return fileList.get(i).getRouter();
        }
      }
      return null;
    }else{
      return null;
    }
  }

}