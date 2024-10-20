package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carriages")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String type;
    byte rows;
    byte leftSeats;
    byte rightSeats;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_carriages",
            joinColumns = @JoinColumn(name = "carriage_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    List<Route> routes = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "carriage", fetch = FetchType.LAZY)
    List<Price> prices = new ArrayList<>();
}
