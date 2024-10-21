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
@Table(name = "rides")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    Route route;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY)
    List<Price> prices = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY)
    List<Schedule> schedules = new ArrayList<>();

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
