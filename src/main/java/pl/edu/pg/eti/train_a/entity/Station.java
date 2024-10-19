package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Station {
    @Id
    int id;

    String city;
    BigDecimal latitude;
    BigDecimal longitude;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_stations",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    List<Route> routes = new ArrayList<>();
}
