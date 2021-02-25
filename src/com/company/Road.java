package com.company;

import java.util.LinkedList;

public class Road {
    public int start;
    public int end;
    public String name;
    public int L;
    public Intersection intersection;

    public boolean isGreenLight;
    public LinkedList<Car> carsInQueue;

    public Road(int start, int end, String name, int l) {
        this.start = start;
        this.end = end;
        this.name = name;
        L = l;
    }
}

