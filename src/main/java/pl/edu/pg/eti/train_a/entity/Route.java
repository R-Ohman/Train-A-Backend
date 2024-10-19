package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    int id;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Carriage> carriages;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Station> stations;

    public static RouteBuilder autoBuilder() {
        return new AutoRouteBuilder();
    }

    public static class AutoRouteBuilder extends RouteBuilder {
        @Override
        public Route build() {
            var route = super.build();
            route.carriages.forEach(carriage -> carriage.getRoutes().add(route));
            route.stations.forEach(station -> station.getRoutes().add(route));
            return route;
        }
    }
}
