package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "railways")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Railway {
    @Id
    @ToString.Exclude
    @Builder.Default
    @Column(name = "id")
    UUID id = UUID.randomUUID();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "railway_stations",
            joinColumns = @JoinColumn(name = "railway_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    List<Station> stations;

    int distance;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "railway", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Price> prices = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "railway", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Schedule> schedules = new ArrayList<>();

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
