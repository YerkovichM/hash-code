package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static Input read(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            int time = scanner.nextInt();
            int in = scanner.nextInt();
            int st = scanner.nextInt();
            int crs = scanner.nextInt();
            int pnt = scanner.nextInt();
            List<Intersection> intersections = Stream.iterate(0, i -> i + 1)
                    .limit(in)
                    .map(i -> {
                        Intersection intersection = new Intersection();
                        intersection.name = i;
                        return intersection;
                    })
                    .collect(Collectors.toList());
            Map<String, Road> roads = new HashMap<>();
            for (int i = 0; i < st; i++) {
                Road road = new Road(scanner.nextInt(), scanner.nextInt(), scanner.next(), scanner.nextInt());
                road.LastTimeSwitched = time;
                roads.put(road.name, road);
                Intersection intersection = intersections.get(road.end);
                intersection.connectedInputRoads.add(road);
                road.intersection = intersection;
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
            return new Input(time, in, pnt, roads, cars, intersections);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(String fileName, List<Intersection> intersections) throws IOException {

        List<Intersection> managedIntersections = intersections.stream()
                .filter(intersection -> intersection
                        .connectedInputRoads
                        .stream()
                        .anyMatch(road -> road.cycle > 0))
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(fileName)) {
//            writer.write(managedIntersections.size() + "\n");
            writer.write(intersections.size() + "\n");
//            for (Intersection intersection : managedIntersections) {
            for (Intersection intersection : intersections) {
                writer.write(intersection.name + "\n");
//                writer.write(calculateManagedRoads(intersection)+ "\n");
                writer.write(intersection.connectedInputRoads.size() + "\n");
                for (Road r : intersection.connectedInputRoads) {
                    if (r.cycle > 0) {
                        writer.write(r.name + " " + r.cycle + "\n");
                    } else  {
                        writer.write(r.name + " " + 1 + "\n");
                    }
                }
            }
            writer.flush();
        }
    }

    public static List<String> getInputFileNames() {
        File inputDirectory = new File("input/");
        return Arrays.stream(inputDirectory.listFiles())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    private static long calculateManagedRoads(Intersection intersection) {
        return intersection.connectedInputRoads
                .stream()
                .filter(road -> road.cycle > 0)
                .count();

    }
}
