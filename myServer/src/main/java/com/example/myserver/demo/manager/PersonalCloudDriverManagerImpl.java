package com.example.myserver.demo.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.User;

import org.springframework.stereotype.Service;

@Service("personalCloudDriverManager")
public class PersonalCloudDriverManagerImpl implements PersonalCloudDriverManager {

  final private String driverRouter = "D:\\Ts\\private";

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
    String rootRouter = this.getUserDir(user);
    if(rootRouter == null || rootRouter.isEmpty()){
      rootRouter = this.createDir(user.getUserName()+"defaultDir");
    }
    return this.getFileList(user, rootRouter);
  }

  private String getUserDir(User user) {
    return null;
  }

  @Override
  public String createDir(String dirName) {
    
    return dirName;
  }

}