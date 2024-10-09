package pl.edu.pg.eti.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CharacterDto implements Comparable<CharacterDto> {
    String name;

    int level;

    String profession;

    @Override
    public int compareTo(CharacterDto o) {
        if (this.name.compareTo(o.name) != 0) {
            return this.name.compareTo(o.name);
        } else if (this.level != o.level) {
            return this.level - o.level;
        } else {
            return this.profession.compareTo(o.profession);
        }
    }
}
