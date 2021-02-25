package com.company;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        Input input = FileUtils.read("");

        final int Duration = 10;

        final int numberOfIntersections = 10;
        final int numberOfCars = 10;

        final List<Car> carList = new ArrayList<Car>();

        for (int i = 0; i < Duration; i++) {

            goThroughIntersections(numberOfIntersections);
            moveAllOn1Second(carList);
        }

    }

    private static void goThroughIntersections(int numberOfIntersections) {


    }

    private static void moveAllOn1Second(List<Car> carList) {
        carList.forEach(car -> {
            processCar(car);

        });

    }

    private static void processCar(Car car) {
        if (car.inQueue) {
            if (car.numberInQueue == 0) {
                checkTrafficLightAndMove(car);
            }
        } else {
            moveCar(car);
        }
    }

    private static void moveCar(Car car) {
        if (car.timeToEndOfRoad > 0) {
            car.timeToEndOfRoad--;
            if (car.timeToEndOfRoad == 0) {
                addCarToQueue(car);
            }
        } else {
            //should never occure
            //dublicate for durability
            addCarToQueue(car);
            processCar(car);
        }
    }

    private static void addCarToQueue(Car car) {
        car.inQueue = true;
        car.currentRoad.carsInQueue.add(car);
    }

    private static void checkTrafficLightAndMove(Car car) {
        if (car.currentRoad.isGreenLight) {
            car.currentRoad.carsInQueue.pop();
            //

        }


        logTrafficLightChange();
    }


    private static void logTrafficLightChange() {
        //todo: implement
    }
}
