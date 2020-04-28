package com.example.myserver.demo.controller.practiseForNewTec;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocketTest/{page}")
public class WebSocketDemo {
    //存放房间信息
    static Map<String, Set<Session>> roomMap = new ConcurrentHashMap<String, Set<Session>>();

    @OnOpen
    public void openSocket(@PathParam("page") String roomId, Session session) {
        Set<Session> room = roomMap.get(roomId);
        if (room == null) {
            room = new CopyOnWriteArraySet<Session>();
            room.add(session);
            roomMap.put(roomId, room);
        } else {
            room.add(session);
        }
    }

    @OnClose
    public void leaveSocket(@PathParam("page") String roomId, Session session) {
        if (roomMap.containsKey(roomId)) {
            roomMap.get(roomId).remove(session);
        }
    }

    @OnMessage
    public void dealMsg(@PathParam("page") String roomId, Session session, String message) throws IOException {
        if (roomMap.containsKey(roomId)) {
            for (Session s : roomMap.get(roomId)) {
                s.getBasicRemote().sendText(session.getId() + ":" + message);
            }
        }
    }

    @OnError
    public void dealError(Throwable throwable) {
        try {
            throw throwable;
        } catch (Throwable e) {
        }
    }
}