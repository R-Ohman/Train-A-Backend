package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    @ManyToOne
    @JoinColumn(name = "carriage_type")
    Carriage carriage;

    @ManyToOne
    @JoinColumn(name = "railway_id")
    Railway railway;

    int price;
}
