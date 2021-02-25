package com.company;

import java.util.List;
import java.util.Map;

public class Input {
    int time;
    int numOfIntersection;
    int points;
    Map<String, Road> roads;
    List<Car> cars;

    public Input(int time, int numOfIntersection, int points, Map<String, Road> roads, List<Car> cars) {
        this.time = time;
        this.numOfIntersection = numOfIntersection;
        this.points = points;
        this.roads = roads;
        this.cars = cars;
    }
}
