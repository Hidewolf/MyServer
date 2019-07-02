package com.example.myserver.demo.service;

import javax.annotation.Resource;

import com.example.myserver.demo.mapper.PersonalCloudDriverMapper;
import com.example.myserver.demo.model.User;

import org.springframework.stereotype.Service;

@Service("personalCloudDriverService")
public class PersonalCloudDriverServiceImpl implements PersonalCloudDriverService {

  @Resource(name="personalCloudDriverMapper")
  PersonalCloudDriverMapper personalCloudDriverMapper;

  @Override
  public int insertCfgInfo(User user, String dirName) {
    return personalCloudDriverMapper.insertCfgInfo(user, dirName);
  }

  @Override
  public User getCfgInfo(User user) {
    return personalCloudDriverMapper.queryCfgInfo(user);
  }

}