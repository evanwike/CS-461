package com.company.Rooms;

import com.company.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rooms {
    private List<Room> rooms;

    public Rooms() {
        this.rooms = new ArrayList<>();

        try {
            processRoomData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processRoomData() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Main.PATH + Main.ROOM_DATA));

        while (scanner.hasNextLine()) {
            String building = scanner.next();
            String roomNum = scanner.next();
            int capacity = scanner.nextInt();
            String[] availableTimes = scanner
                    .skip(" ")
                    .nextLine()
                    .split(" ");

            rooms.add(new Room(building, roomNum, capacity, availableTimes));
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getNumRooms() { return rooms.size(); }

    @Override
    public String toString() {
        return "Rooms{" +
                "rooms=" + rooms +
                '}';
    }
}
