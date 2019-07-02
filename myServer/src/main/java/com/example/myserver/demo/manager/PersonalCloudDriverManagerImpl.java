package com.example.myserver.demo.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.example.myserver.demo.mapper.PersonalCloudDriverMapper;
import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder.RES_ENUM;
import com.example.myserver.demo.service.PersonalCloudDriverService;

import org.springframework.stereotype.Service;

@Service("personalCloudDriverManager")
public class PersonalCloudDriverManagerImpl implements PersonalCloudDriverManager {

  final private String driverRouter = "D:\\Ts\\private";

  @Resource(name = "personalCloudDriverService")
  PersonalCloudDriverService personalCloudDriverService;

  @Resource(name = "resultBuilder")
  CommonResultBuilder resultBuilder;

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
    String rootRouter = this.getUserDir(user).getInfo().toString();
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
      CommonResult<String> res = this.createDir(user, "", dirName);
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
    if (rootDirName != null && !rootDirName.isEmpty()) {
      User cfgInfo = this.getUserDir(user);
      if (rootDirName.indexOf("\\" + cfgInfo.getInfo().toString() + "\\") < 0) {
        return resultBuilder.Error(RES_ENUM.NOT_CURRENT_INFO);
      }
    }

    File dir = new File(driverRouter + dirName);
    boolean res = dir.mkdir();

    if (res) {
      return resultBuilder.Success(dirName);
    } else {
      return resultBuilder.Error(RES_ENUM.NOT_CURRENT_INFO, "Path incompleteness");
    }
  }

}