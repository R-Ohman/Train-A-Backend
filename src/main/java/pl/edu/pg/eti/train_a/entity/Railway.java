package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "railways")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Railway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "station1")
    Station station1;

    @ManyToOne
    @JoinColumn(name = "station2")
    Station station2;

    int distance;
}
