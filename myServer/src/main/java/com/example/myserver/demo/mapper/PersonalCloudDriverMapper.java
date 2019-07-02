package com.example.myserver.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.myserver.demo.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@Component("personalCloudDriverMapper")
public interface PersonalCloudDriverMapper{
  int insertCfgInfo(@Param("user") User user,@Param("dirName") String dirName);

  User queryCfgInfo(@Param("user") User user);
}