package com.example.myserver.demo.service;

import com.example.myserver.demo.model.User;

public interface PersonalCloudDriverService {
  int insertCfgInfo(User user,String dirName);

  User getCfgInfo(User user);
}