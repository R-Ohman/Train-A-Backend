package pl.edu.pg.eti.entities;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(buildMethodName = "buildInternal")
public class Character implements Serializable {
    String name;

    int level;

    Profession profession;

    public static class CharacterBuilder {
        public Character build() {
            var character = this.buildInternal();
            character.getProfession().getCharacters().add(character);
            return character;
        }
    }
}
