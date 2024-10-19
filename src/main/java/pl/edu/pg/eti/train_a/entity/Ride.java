package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rides")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    Route route;

    public static RideBuilder autoBuilder() {
        return new AutoRideBuilder();
    }

    public static class AutoRideBuilder extends RideBuilder {
        @Override
        public Ride build() {
            var ride = super.build();
            ride.route.getRides().add(ride);
            return ride;
        }
    }
}
