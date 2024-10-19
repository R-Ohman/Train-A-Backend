package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    User user;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    int seatId;

    @ManyToOne
    @JoinColumn(name = "station_start")
    Station stationStart;

    @ManyToOne
    @JoinColumn(name = "station_end")
    Station stationEnd;

    String status;

    public static OrderBuilder autoBuilder() {
        return new AutoOrderBuilder();
    }

    public static class AutoOrderBuilder extends OrderBuilder {
        @Override
        public Order build() {
            var order = super.build();
            order.getUser().getOrders().add(order);
            order.getRide().getOrders().add(order);
            order.getStationStart().getOrdersFrom().add(order);
            order.getStationEnd().getOrdersTo().add(order);
            return order;
        }
    }
}
