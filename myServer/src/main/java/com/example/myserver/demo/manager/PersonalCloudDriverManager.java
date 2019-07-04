package com.example.myserver.demo.manager;

import java.util.List;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;

public interface PersonalCloudDriverManager{
  List<CloudDriverFile> getFileList(User user,String rootRouter);
  List<CloudDriverFile> getFileList(User user);
  CommonResult createDir(User user,String rootDirName,String dirName);
  String createRootDir(User user,String dirName);
}