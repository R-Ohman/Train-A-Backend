package pl.edu.pg.eti.train_a.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    int seatId;

    @ManyToOne
    @JoinColumn(name = "station_start")
    @JsonIgnore
    Station stationStart;

    @ManyToOne
    @JoinColumn(name = "station_end")
    @JsonIgnore
    Station stationEnd;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.ACTIVE;
}
