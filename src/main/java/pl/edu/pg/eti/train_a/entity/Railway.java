package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "railways")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Railway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "railway_stations",
            joinColumns = @JoinColumn(name = "railway_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    List<Station> stations;

    int distance;

    public static RailwayBuilder autoBuilder() {
        return new AutoRailwayBuilder();
    }

    public static class AutoRailwayBuilder extends RailwayBuilder {
        @Override
        public Railway build() {
            var railway = super.build();
            if (railway.stations.size() != 2) {
                throw new IllegalArgumentException("A railway must connect exactly two stations.");
            }
            railway.stations.forEach(station -> station.getRailways().add(railway));
            railway.distance = (int) Math.round(
                    111.32 * Math.sqrt(
                            Math.pow(railway.stations.get(0).getLatitude().doubleValue() - railway.stations.get(1).getLatitude().doubleValue(), 2) +
                                    Math.pow((railway.stations.get(0).getLongitude().doubleValue() - railway.stations.get(1).getLongitude().doubleValue()) * Math.cos(Math.toRadians((railway.stations.get(0).getLatitude().doubleValue() + railway.stations.get(1).getLatitude().doubleValue()) / 2)), 2)
                    )
            );
            return railway;
        }
    }
}
