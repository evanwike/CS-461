package com.company.Rooms;

import java.util.Arrays;

public class Room {
    private String building;
    private String roomNum;
    private int capacity;
    private String[] availableTimes;

    public Room(String building, String roomNum, int capacity, String[] availableTimes) {
        this.building = building;
        this.roomNum = roomNum;
        this.capacity = capacity;
        this.availableTimes = availableTimes;
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

    public String[] getAvailableTimes() {
        return availableTimes;
    }

    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", capacity=" + capacity +
                ", availableTimes=" + Arrays.toString(availableTimes) +
                '}';
    }
}
