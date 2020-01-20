package com.example.myserver.demo.applications;

import com.example.myserver.demo.manager.GameManager.GameData;
import com.example.myserver.demo.model.GameRoom;
import com.example.myserver.demo.model.RoomInfo;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.staticClass.GAME_NAME;
import com.example.myserver.demo.staticClass.STATUS_STATIC;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("gameRoomsApplication")
public class GameRoomsApplication {

    private static Map<String, List<GameRoom>> roomMap;

    /**
     * 获取指定游戏的房间列表.
     *
     * @return List<RoomInfo> 只包含房间基本信息</>
     */
    public List<RoomInfo> getRoomsList(String gameName) {
        List<GameRoom> roomList = roomsList(gameName);

        List<RoomInfo> infoList = new ArrayList<>();
        for (GameRoom gr : roomList) {
            if (gr.getStatus() != STATUS_STATIC.OVER) {
                infoList.add(gr);
            }
        }
        return infoList;
    }

    /**
     * 创建房间.
     *
     * @param gameName 游戏名称
     * @return RoomInfo 创建完的房间对象的基本信息
     */
    public synchronized RoomInfo createRoom(String gameName, User user, GameData gameData) {
        if (roomMap == null) {
            roomMap = new HashMap<>();
        }
        List<GameRoom> roomList = roomMap.get(gameName);
        if (roomList == null || roomList.size() == 0) {
            roomList = new ArrayList<>();
            roomMap.put(gameName, roomList);
        }
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGameName(gameName);
        gameRoom.setMaxPlayerNum(5);
        gameRoom.setStatus(STATUS_STATIC.WAIT);
        gameRoom.setHostId(user.getId());
        gameRoom.setHostName(user.getUserName());
        gameRoom.setRoomNo(this.getLastRoomNo(GAME_NAME.INDIAN_TREASURES));
        gameRoom.setPlayerNum(0);
        gameRoom.setGameData(gameData);
        roomList.add(gameRoom);
        return gameRoom;
    }


    public void joinRoom(String roomNo, String gameName, User user) throws Exception {
        GameRoom room = room(gameName, roomNo);
        if (room == null) {
            throw new Exception(CommonResultBuilder.RES_ENUM.NOT_EXISTS_INFO.getCode());
        }
        String res = room.addPlayer(user);
        if (!res.equals(CommonResultBuilder.RES_ENUM.SUCCESS.getCode())) {
            throw new Exception(res);
        }
    }

    /**
     * 根据最新的房间号创建新的房间号.
     *
     * @param gameName
     * @return
     */
    private String getLastRoomNo(String gameName) {
        DecimalFormat decimalFormat = new DecimalFormat("000");
        if (roomMap == null) {
            return "001";
        }
        List<GameRoom> roomList = roomMap.get(gameName);
        if (roomList == null || roomList.size() == 0) {
            return "001";
        }
        return decimalFormat.format(Integer.parseInt(roomList.get(roomList.size() - 1).getRoomNo()) + 1);
    }

    /**
     * 获取指定游戏的房间列表.
     *
     * @param gameName
     * @return
     */
    private List<GameRoom> roomsList(String gameName) {
        if (roomMap == null) {
            return null;
        }
        List<GameRoom> roomList = roomMap.get(gameName);
        if (roomList == null || roomList.size() == 0) {
            return null;
        }
        return roomList;
    }

    /**
     * 获取指定房间.
     *
     * @param gameName
     * @param RoomNo
     * @return
     */
    private GameRoom room(String gameName, String RoomNo) {
        List<GameRoom> gameRooms = this.roomsList(gameName);
        for (GameRoom room : gameRooms) {
            if (room.getRoomNo().equals(RoomNo)) {
                return room;
            }
        }
        return null;
    }
}
