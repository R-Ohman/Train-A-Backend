package pl.edu.pg.eti;

import pl.edu.pg.eti.dtos.CharacterDto;
import pl.edu.pg.eti.entities.Character;
import pl.edu.pg.eti.entities.Profession;

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

        var professions = getProfessionsStream(3).toList();
        getCharactersStream(professions, 7).toList();

        professions.forEach(profession -> {
            System.out.println(profession.getName());
            profession.getCharacters().forEach(character -> System.out.printf("\t%s\n", character.toString()));
        });

        System.out.println("\nTASK 3\n");

        var characters = professions.stream()
                .flatMap(profession -> profession.getCharacters().stream())
                .collect(Collectors.toSet());
        characters.forEach(System.out::println);

        System.out.println("\nTASK 4\n");

        characters.stream()
                .filter(x -> x.getLevel() > 4)
                .sorted(Comparator.comparing(Character::getName))
                .forEach(System.out::println);

        System.out.println("\nTASK 5\n");

        var characterDtos = characters.stream()
                .map(character -> CharacterDto.builder()
                        .name(character.getName())
                        .level(character.getLevel())
                        .profession(character.getProfession().getName())
                        .build()
                )
                .sorted()
                .toList();
        characterDtos.forEach(System.out::println);

        System.out.println("\nTASK 6\n");

        var serializeFileName = "professions.bin";

        serialize(professions, serializeFileName);

        deserialize(serializeFileName)
                .forEach(profession -> {
                    System.out.println(profession.getName());
                    profession.getCharacters()
                            .forEach(character -> System.out.printf("\t%s\n", character.toString()));
            });

        System.out.println("\nTASK 7\n");

        var pool = new ForkJoinPool(4);
        pool.submit(() -> characters.parallelStream().forEach(character -> {
            try {
                Thread.sleep(character.getLevel() * 500L);
                System.out.println(character);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).join();
    }

    private static void serialize(List<Profession> professions, String fileName) {
        try (
                var fileOutputStream = new FileOutputStream(fileName);
                var objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(professions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Profession> deserialize(String fileName) {
        try (
                var fileInputStream = new FileInputStream(fileName);
                var objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return ((List<Profession>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Profession> getProfessionsStream(int professionCount) {
        return IntStream.range(1, professionCount + 1).mapToObj(i -> {
            var randomBaseArmor = (int)(Math.random() * 10) + 1;
            return Profession.builder()
                    .name("Profession " + i)
                    .baseArmor(randomBaseArmor)
                    .build();
        });
    }

    private static Stream<Character> getCharactersStream(List<Profession> professions, int characterCount) {
        return IntStream.range(1, characterCount + 1).mapToObj(i -> {
            var randomProfessionIdx = (int)(Math.random() * professions.size());
            var randomProfession = professions.get(randomProfessionIdx);
            var randomLevel = (int)(Math.random() * 10) + 1;
            return Character.builder()
                    .name("Character " + i)
                    .level(randomLevel)
                    .profession(randomProfession)
                    .build();
        });
    }
}
