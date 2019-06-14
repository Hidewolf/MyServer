package com.example.myserver.demo.model;

import lombok.Data;

@Data
public class CommonResult<T> {
  private String resultCode;
  private String resultMsg;
  private String errInfo;

  private T res;

}