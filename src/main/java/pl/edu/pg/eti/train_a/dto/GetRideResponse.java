package pl.edu.pg.eti.train_a.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetRideResponse {
    private int rideId;
    private int routeId;
    private List<UUID> path;
    private List<String> carriages;
    private Schedule schedule;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Schedule {

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Builder
        public static class Segment {
            private List<LocalDateTime> time;
            private Map<String, BigDecimal> price;
            private List<Integer> occupiedSeats;
        }
        private List<Segment> segments;
    }
}
