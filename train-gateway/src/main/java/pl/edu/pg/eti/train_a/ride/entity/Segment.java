package pl.edu.pg.eti.train_a.ride.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ride_segments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    LocalDateTime arrival;

    LocalDateTime departure;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ride_segment_prices", joinColumns = @JoinColumn(name = "ride_segment_id"))
    @MapKeyJoinColumn(name = "carriage_id")
    @Column(name = "price")
    Map<Carriage, BigDecimal> prices;
}
