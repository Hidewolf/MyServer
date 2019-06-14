package com.example.myserver.demo.manager;

import com.example.myserver.demo.model.User;

public interface LogCheckManager {
  boolean checkLog(String userName, String psw);

  User getUserInfo(String userName);

  boolean checkUserExists(String userName);

  boolean addUser(String userName, String psw);
}
