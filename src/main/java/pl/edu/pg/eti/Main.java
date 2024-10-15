package pl.edu.pg.eti;

import pl.edu.pg.eti.dtos.CarriageDto;
import pl.edu.pg.eti.entities.Carriage;
import pl.edu.pg.eti.entities.Train;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nTASK 2\n");

        var trains = getTrainsStream(3).toList();
        getCarriagesStream(trains, 7).toList();

        trains.forEach(train -> {
            System.out.println(train.getName());
            train.getCarriages().forEach(carriage -> System.out.printf("\t%s\n", carriage.toString()));
        });

        System.out.println("\nTASK 3\n");

        var carriages = trains.stream()
                .flatMap(train -> train.getCarriages().stream())
                .collect(Collectors.toSet());
        carriages.forEach(System.out::println);

        System.out.println("\nTASK 4\n");

        carriages.stream()
                .filter(x -> x.getNumber() > 2)
                .sorted(Comparator.comparing(Carriage::getType))
                .forEach(System.out::println);

        System.out.println("\nTASK 5\n");

        var carriageDtos = carriages.stream()
                .map(carriage -> CarriageDto.builder()
                        .type(carriage.getType())
                        .number(carriage.getNumber())
                        .train(carriage.getTrain().getName())
                        .build()
                )
                .sorted()
                .toList();
        carriageDtos.forEach(System.out::println);

        System.out.println("\nTASK 6\n");

        var serializeFileName = "trains.bin";

        serialize(trains, serializeFileName);

        deserialize(serializeFileName)
                .forEach(train -> {
                    System.out.println(train.getName());
                    train.getCarriages()
                            .forEach(carriage -> System.out.printf("\t%s\n", carriage.toString()));
            });

        System.out.println("\nTASK 7\n");

        var pool = new ForkJoinPool(2);
        pool.submit(() -> carriages.parallelStream().forEach(carriage -> {
            try {
                Thread.sleep(carriage.getNumber() * 500L);
                System.out.println(carriage);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).join();
    }

    private static void serialize(List<Train> trains, String fileName) {
        try (
                var fileOutputStream = new FileOutputStream(fileName);
                var objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(trains);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Train> deserialize(String fileName) {
        try (
                var fileInputStream = new FileInputStream(fileName);
                var objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return ((List<Train>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Train> getTrainsStream(int trainCount) {
        return IntStream.range(1, trainCount + 1).mapToObj(i -> {
            var randomTrainCode = (int)(Math.random() * 1000) + 1;
            return Train.builder()
                    .name("Train " + i)
                    .code(randomTrainCode)
                    .build();
        });
    }

    private static Stream<Carriage> getCarriagesStream(List<Train> trains, int maxCarriageCount) {
        return trains.stream().flatMap(train -> {
            var randomCarriageNumber = (int)(Math.random() * maxCarriageCount) + 1;
            return IntStream.range(1, randomCarriageNumber + 1).mapToObj(i -> {
                var randomCarriageClass = (int)(Math.random() * 3) + 1;
                return Carriage.builder()
                        .type("Class " + randomCarriageClass)
                        .number(i)
                        .train(train)
                        .build();
            });
        });
    }
}
