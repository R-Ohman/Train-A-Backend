package pl.edu.pg.eti.train_a.dto;

import lombok.*;
import pl.edu.pg.eti.train_a.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

        private int id;
        private int rideId;
        private int routeId;
        private int seatId;
        private int userId;
        private OrderStatus status;
        private List<Integer> path;
        private List<String> carriages;
        private Schedule schedule;
    }

    private List<Order> orders;
}
