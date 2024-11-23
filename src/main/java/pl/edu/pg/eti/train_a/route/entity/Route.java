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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Ride> rides = new ArrayList<>();

    public static RouteBuilder autoBuilder() {
        return new AutoRouteBuilder();
    }

    public static class AutoRouteBuilder extends RouteBuilder {

        @Override
        public Route build() {
            var route = super.build();
//            route.carriages.forEach(carriage -> carriage.getRoutes().add(route));
            route.stations.forEach(station -> station.getRoutes().add(route));
            return route;
        }
    }
}
