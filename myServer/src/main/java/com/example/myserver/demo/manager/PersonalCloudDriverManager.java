package com.example.myserver.demo.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.myserver.demo.model.CloudDriverFile;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.User;

public interface PersonalCloudDriverManager {
  List<CloudDriverFile> getFileList(User user, String rootRouter);

  List<CloudDriverFile> getFileList(User user);

  CommonResult<String> createDir(User user, String rootDirName, String dirName);

  String createRootDir(User user, String dirName);

  String getFileSecret(HttpSession session, HttpServletRequest req);

  void secretFileInfo(HttpSession session, List<CloudDriverFile> fileList);
}