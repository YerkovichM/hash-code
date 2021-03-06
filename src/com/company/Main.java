package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.FileUtils.getInputFileNames;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> processingFiles =  getInputFileNames();
        for (String processingFile : processingFiles) {
            processFile(processingFile);
        }
    }

    private static void processFile(String fileName) throws IOException  {
        Input input = FileUtils.read("input/" + fileName);

        final int Duration = input.time;

        List<Intersection> intersections = input.intersections;
        List<Road> roads = new ArrayList<>(input.roads.values());
        final List<Car> carList = input.cars;
        for (int i = 0; i < Duration; i++ ) {

            goThroughIntersections(intersections, i);
            moveAllOn1Second(carList, i);
        }

        roads.forEach(road -> {
//            if(road.isGreenLight) {
//                road.turnOnsSeconds.add(Duration - road.LastTimeSwitched);
//            }

            road.cycle = (int)Math.ceil((double)(road.turnOnsSeconds.stream().mapToInt(Integer::intValue).sum()) / road.turnOnsSeconds.size());
        });
        FileUtils.write("result/" + fileName, intersections);
    }


    private static void goThroughIntersections (List<Intersection> intersections, int turn) {
        intersections.forEach(intersection -> {
            Road changing = getHighestPriorityRoad(intersection);
            Road lastTurned = getLastTurnedRoad(intersection);

            boolean changed = false;

            if(changing != null && changing != lastTurned && !changing.carsInQueue.isEmpty()) {
                changing.isGreenLight = true;
                changing.turnedOn  =turn;
                changed = true;
            }

            if(changed && lastTurned != null) {
                turnOffLight(lastTurned, turn);
//                changed = false;
            }

            //turn off if not used
            if(lastTurned != null && lastTurned.carsInQueue.isEmpty()) {
                turnOffLight(lastTurned, turn);
            }

        });
    }

    private static void turnOffLight(Road road, int turn) {
        road.isGreenLight = false;

        road.turnOnsSeconds.add(turn - road.turnedOn);
    }


    private static void moveAllOn1Second (List<Car> carList, int step) {
        carList.forEach(car -> {
            processCar(car, step);

        });

    }

    private static void processCar(Car car, int step) {
        if(car.inQueue) {
            if(car.numberInQueue == 0) {
                checkTrafficLightAndMove(car, step);
            }
        } else {
            moveCar(car, step);
        }
    }

    private static void moveCar (Car car, int step) {
        if(car.timeToEndOfRoad > 0) {
            car.timeToEndOfRoad--;
            if (car.timeToEndOfRoad == 0) {
                addCarToQueue(car);
            }
        } else {
            //should never occure
            //dublicate for durability
            addCarToQueue(car);
            processCar(car, step);
        }
    }

    private static void addCarToQueue (Car car){
        car.inQueue = true;
        car.currentRoad.carsInQueue.add(car);
    }

    private static void checkTrafficLightAndMove(Car car, int step){

        if(car.currentRoad.isGreenLight && step != car.currentRoad.intersection.lastTurnUsed) {
            switchRoad(car, step);
        }
    }




    private static void switchRoad(Car car, int step) {
        car.currentRoad.carsInQueue.pop();

        car.currentRoad.carsInQueue.forEach(car1-> {
            car1.numberInQueue--;
        });

        car.roadId++;
        car.inQueue = false;
        car.currentRoad.intersection.lastTurnUsed = step;

        if(car.roadId < car.roads.size()) {
            car.currentRoad = car.roads.get(car.roadId);
            car.timeToEndOfRoad = car.currentRoad.L;
        } else {

            //end of roads
        }
    }


    private static Road getLastTurnedRoad(Intersection intersection) {

        for(Road road : intersection.connectedInputRoads) {
            if(road.isGreenLight) {
                return road;
            }
        }


        return null;
    }

    private static Road getHighestPriorityRoad(Intersection intersection) {

        List<Road> competitors = intersection.connectedInputRoads;
        int maxCars = -1;
        Road topPriority = null;

        for(Road road : competitors) {
            if(road.carsInQueue.size() > maxCars) {
                topPriority = road;
                maxCars = road.carsInQueue.size();
            }
        }

        return topPriority;
    }
}
