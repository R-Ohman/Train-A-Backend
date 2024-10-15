package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carriages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String type;
    byte rows;
    byte leftSeats;
    byte rightSeats;
}
