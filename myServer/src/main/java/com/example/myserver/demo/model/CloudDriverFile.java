package com.example.myserver.demo.model;

import java.io.File;
import java.io.Serializable;

import lombok.Data;

@Data
public class CloudDriverFile{
  
  private String fileName;
  private String owner;
  private String router;
  private String type;
  // 文件序号，用于保护文件在服务器的路径
  private String index;

  public CloudDriverFile(File file, User user, int index) {
    this.fileName = file.getName();
    this.owner = Integer.toString(user.getId());
    this.router = file.getPath();
    this.index = Integer.toString(index);
    this.type = file.isFile() ? "file" : "dir";
  }

  @Override
  public String toString() {
    return "fileName:" + this.fileName + "-router:" + this.router + "-type:" + this.type + "-index:" + this.index;
  }
}