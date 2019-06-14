package com.example.myserver.demo.manager;

import java.util.List;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.User;

import org.springframework.stereotype.Service;

@Service("personalCloudDriverManager")
public class PersonalCloudDriverManagerImpl implements PersonalCloudDriverManager {

  @Override
  public List<CloudDriverFile> getFileList(User user, String rootRouter) {
    return null;
  }

  @Override
  public List<CloudDriverFile> getFileList(User user) {
    String rootRouter = this.getUserDir(user);
    return this.getFileList(user, rootRouter);
  }

  private String getUserDir(User user) {
    return null;
  }

}