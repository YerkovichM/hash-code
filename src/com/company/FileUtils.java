package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileUtils {
    public static Input read(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            int time = scanner.nextInt();
            int in = scanner.nextInt();
            int st = scanner.nextInt();
            int crs = scanner.nextInt();
            int pnt = scanner.nextInt();
            Map<String, Road> roads = new HashMap<>();
            for (int i = 0; i < st; i++) {
                Road road = new Road(scanner.nextInt(), scanner.nextInt(), scanner.next(), scanner.nextInt());
                roads.put(road.name, road);
            }
            List<Car> cars = new ArrayList<>();
            for (int i = 0; i < crs; i++) {
                int j = scanner.nextInt();
                List<Road> localRoads = new ArrayList<>();
                for (int k = 0; k < j; k++) {
                    localRoads.add(roads.get(scanner.next()));
                }
                cars.add(new Car(localRoads));
            }
            return new Input(time, in, pnt, roads, cars);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}