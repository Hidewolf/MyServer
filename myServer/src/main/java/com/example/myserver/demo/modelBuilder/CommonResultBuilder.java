package com.example.myserver.demo.modelBuilder;

import com.example.myserver.demo.model.CommonResult;
import org.springframework.stereotype.Component;

@Component("resultBuilder")
public class CommonResultBuilder<T> {
  public enum RES_ENUM {
    // 成功
    SUCCESS("200", "success"),
    // 失败
    FAIL("500", "error with unknown or normal cause"),
    // 缺失必要信息
    LOST_NECESSARY_IMFORMATION("503", "something necessary haven't input"),
    // 信息不正确
    NOT_CURRENT_INFO("502", "not all infomation current"),
    // 内容重复
    REPEAT_CONTENT("110", "the content is repeat"),
    // 没有权限
    INSUFFICIENT_PRIVILEGES("220", "you have no access to do this");

    private String code;
    private String msg;

    private RES_ENUM(String code, String msg) {
      this.code = code;
      this.msg = msg;
    }
  }

  public CommonResult<T> Success() {
    return this.Success(null);
  }

  public CommonResult<T> Success(T res) {
    CommonResult<T> commonResult = new CommonResult();
    commonResult.setResultCode(RES_ENUM.SUCCESS.code);
    commonResult.setResultMsg(RES_ENUM.SUCCESS.msg);
    commonResult.setRes(res);
    return commonResult;
  }

  public CommonResult<T> Error() {
    return this.Error(RES_ENUM.FAIL);
  }

  public CommonResult<T> Error(RES_ENUM errorType) {
    return this.Error(errorType, "");
  }

  public CommonResult<T> Error(RES_ENUM errorType, String errInfo) {
    CommonResult<T> commonResult = new CommonResult();
    commonResult.setResultCode(errorType.code);
    commonResult.setResultMsg(errorType.msg);
    commonResult.setErrInfo(errInfo);
    return commonResult;
  }
}