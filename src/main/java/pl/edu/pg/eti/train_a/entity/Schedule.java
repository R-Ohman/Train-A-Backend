package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "railway_id")
    Railway railway;

    LocalDateTime arrivalTime;
    LocalDateTime departureTime;
}