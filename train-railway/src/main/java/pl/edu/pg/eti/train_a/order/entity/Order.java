package pl.edu.pg.eti.train_a.order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.ride.entity.Ride;

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
    @JoinColumn(name = "ride_id")
    Ride ride;

    int seatId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.ACTIVE;
}
