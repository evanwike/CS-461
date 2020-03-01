package com.company;

public class Room {
    private String building;
    private String roomNum;
    private int capacity;

    public Room(String building, String roomNum, int capacity) {
        this.building = building;
        this.roomNum = roomNum;
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public int getCapacity() {
        return capacity;
    }
}