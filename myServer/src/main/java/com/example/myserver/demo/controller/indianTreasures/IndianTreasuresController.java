package com.example.myserver.demo.controller.indianTreasures;

import com.example.myserver.demo.applications.GameRoomsApplication;
import com.example.myserver.demo.manager.GameManager.GameData;
import com.example.myserver.demo.model.CommonResult;
import com.example.myserver.demo.model.RoomInfo;
import com.example.myserver.demo.model.User;
import com.example.myserver.demo.modelBuilder.CommonResultBuilder;
import com.example.myserver.demo.staticClass.GAME_NAME;
import com.example.myserver.demo.staticClass.PARAMS_KEY;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://192.168.7.194:8040", allowCredentials = "true")
@RequestMapping("/indianTreasures")
public class IndianTreasuresController {
    @Resource
    GameRoomsApplication gameRoomsApplication;

    @Resource
    GameData indianTreasuresData;

    /**
     * 获取房间列表
     */
    @RequestMapping("/rooms")
    @ResponseBody
    public CommonResult<List<RoomInfo>> getRooms() {
        CommonResultBuilder<List<RoomInfo>> resultBuilder = new CommonResultBuilder();
        return resultBuilder.Success(gameRoomsApplication.getRoomsList(GAME_NAME.INDIAN_TREASURES));
    }

    /**
     * 创建房间
     */
    @RequestMapping("/createRoom")
    @ResponseBody
    public CommonResult<RoomInfo> createRoom(HttpServletRequest req) {
        CommonResultBuilder<RoomInfo> resultBuilder = new CommonResultBuilder<>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);

        RoomInfo room = gameRoomsApplication.createRoom(GAME_NAME.INDIAN_TREASURES, user, indianTreasuresData);

        return resultBuilder.Success(room);
    }

    /**
     * 进入房间
     */
    @RequestMapping("/joinRoom")
    @ResponseBody
    public CommonResult<RoomInfo> joinRoom(HttpServletRequest req) {
        CommonResultBuilder<RoomInfo> resultBuilder = new CommonResultBuilder<>();
        HttpSession session = req.getSession();

        String roomNo = req.getParameter("roomNo");
        User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);

        try {
            gameRoomsApplication.joinRoom(roomNo, GAME_NAME.INDIAN_TREASURES, user);
        } catch (Exception e) {
            e.printStackTrace();
            return resultBuilder.Error(e.getMessage());
        }

        return resultBuilder.Success(null);
    }
}
