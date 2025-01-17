package pl.edu.pg.eti.train_a.station.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
