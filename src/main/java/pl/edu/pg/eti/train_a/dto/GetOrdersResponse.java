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
public class GetOrdersResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Order {

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
            }
            private List<Segment> segments;
        }

        private UUID id;
        private int rideId;
        private int routeId;
        private int seatId;
        private UUID userId;
        private String status;
        private List<UUID> path;
        private List<String> carriages;
        private Schedule schedule;
    }

    private List<Order> orders;
}
