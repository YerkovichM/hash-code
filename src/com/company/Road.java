package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Road {
    public int start;
    public int end;
    public String name;
    public int L;

    public int LastTimeSwitched = - 1;
    public boolean isGreenLight;
    public LinkedList<Car> carsInQueue;

    public Intersection intersection;

    public List<Integer> turnOnsSeconds = new ArrayList<>();
    public int turnedOn;
    public int cycle;


    public Road(int start, int end, String name, int l) {
        this.start = start;
        this.end = end;
        this.name = name;
        L = l;
    }
}
