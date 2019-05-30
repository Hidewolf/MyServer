package com.example.myserver.demo.mapper;

import com.example.myserver.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("userInfoMapper")
public interface UserInfoMapper {
    List<User> selectUser(@Param("params")Map<String,Object> params);


    boolean insertUser(Map<String, Object> params);
}
