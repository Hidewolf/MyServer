package com.example.myserver.demo.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String userName;
    private int status;
    private String remark;
    private int roleNo;
    private int roleLevel;
    /*特殊属性，用于暂存用户绑定的信息*/
    private Object info;

    public boolean isEmpty() {
        if (this.userName.equals("") || this.userName.isEmpty()) {
            return true;
        }
        if (this.status != 1) {
            return true;
        }
        return false;
    }

    public boolean ifContainRole(int roleId){
      String roleIdBi = Integer.toBinaryString(roleId);
      String roleNoBi = String.format("%0" + roleIdBi.length() + "d", Integer.parseInt(Integer.toBinaryString(this.roleNo)));

      if(roleNoBi.substring(roleIdBi.length()-1, roleIdBi.length()).equals("1")||roleNoBi.substring(0,1).equals("1")){
        return true;
      }else{
        return false;
      }

    }
}
