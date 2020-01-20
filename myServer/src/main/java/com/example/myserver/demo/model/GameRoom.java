package com.example.myserver.demo.model;

import com.example.myserver.demo.manager.GameManager.GameData;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import lombok.Data;

import javax.websocket.Session;
import java.util.Map;

@Data
public class GameRoom extends RoomInfo {
    private Map<String, Session> players;
    private GameData gameData;

    @Override
    public String addPlayer(User user) {
        //检验玩家数是否达到上限
        if(this.getMaxPlayerNum() <= this.getPlayerNum()){
            return "too many players";
        }
        //检验房间状态是否可以加入
        if(this.getStatus()!=0){
            return "invalid status";
        }
        //加入房间
        return CommonResultBuilder.RES_ENUM.SUCCESS.getCode();
    }
}
