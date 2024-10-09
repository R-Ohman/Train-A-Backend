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

        var professions = getProfessionsStream().toList();
        getCharactersStream(professions).toList();

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

        var professionCharacterDtos = characters.stream()
                .map(character -> CharacterDto.builder()
                        .name(character.getName())
                        .level(character.getLevel())
                        .profession(character.getProfession().getName())
                        .build()
                )
                .sorted()
                .toList();
        professionCharacterDtos.forEach(System.out::println);

        System.out.println("\nTASK 6\n");

        // Save
        try (
                var fileOutputStream = new FileOutputStream("professions.bin");
                var objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(professions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read
        try (
                var fileInputStream = new FileInputStream("professions.bin");
                var objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            ((List<Profession>) objectInputStream.readObject())
                    .forEach(profession -> {
                        System.out.println(profession.getName());
                        profession.getCharacters().forEach(character -> System.out.printf("\t%s\n", character.toString()));
                    });
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nTASK 7\n");

        var pool = new ForkJoinPool(4);
        pool.submit(() -> characters.stream().toList().parallelStream().forEach(character -> {
            try {
                Thread.sleep(character.getLevel() * 500L);
                System.out.println(character);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        })).join();
    }

    private static Stream<Profession> getProfessionsStream() {
        return IntStream.range(1, 4).mapToObj(i -> {
            var randomBaseArmor = (int)(Math.random() * 10) + 1;
            return Profession.builder()
                    .name("Profession " + i)
                    .baseArmor(randomBaseArmor)
                    .build();
        });
    }

    private static Stream<Character> getCharactersStream(List<Profession> professions) {
        return IntStream.range(1, 8).mapToObj(i -> {
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
