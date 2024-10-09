package pl.edu.pg.eti.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Profession implements Serializable {
    String name;

    int baseArmor;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    List<Character> characters = new ArrayList<>();
}
