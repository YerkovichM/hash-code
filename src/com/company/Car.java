package com.company;

import java.util.List;

public class Car {
    public List<Road> roads;
    public int timeToEndOfRoad;
    public boolean inQueue;
    public Road currentRoad;
    public int numberInQueue;

    public Car(List<Road> roads) {
        this.roads = roads;
    }
}
