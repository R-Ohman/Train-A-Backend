package pl.edu.pg.eti.train_a.station.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.route.entity.Route;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonIgnore
    List<Route> routes = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "stations", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Railway> railways = new ArrayList<>();
}
