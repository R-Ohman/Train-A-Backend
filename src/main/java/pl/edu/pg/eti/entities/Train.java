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
public class Train implements Serializable {
    String name;

    int code;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    List<Carriage> carriages = new ArrayList<>();
}
