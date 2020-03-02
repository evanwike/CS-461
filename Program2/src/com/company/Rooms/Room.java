package com.company.Rooms;

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

    public Room getCopy() {
        return new Room(this.building, this.roomNum, this.capacity, this.availableTimes.clone());
    }

    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
