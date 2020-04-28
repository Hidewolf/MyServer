package com.example.myserver.demo.model;

import lombok.Data;

@Data
public abstract class RoomInfo {
    private String gameName;
    private String roomNo;
    private String hostName;
    private int hostId;
    private int maxPlayerNum;
    private int playerNum;
    private int status;

    public abstract String addPlayer(User user);
}
