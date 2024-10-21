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
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    @ManyToOne
    @JoinColumn(name = "railway_id")
    Railway railway;

    LocalDateTime arrivalTime;
    LocalDateTime departureTime;

    public static ScheduleBuilder autoBuilder() {
        return new AutoScheduleBuilder();
    }

    public static class AutoScheduleBuilder extends ScheduleBuilder {
        @Override
        public Schedule build() {
            var schedule = super.build();
            schedule.getRailway().getSchedules().add(schedule);
            schedule.getRide().getSchedules().add(schedule);
            return schedule;
        }
    }
}