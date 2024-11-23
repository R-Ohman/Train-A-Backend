package pl.edu.pg.eti.train_a.route.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    List<Carriage> carriages;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    List<Station> stations;
}
