package pl.edu.pg.eti.train_a.ride.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetSearchResponse {
    private Station from;
    private Station to;
    private List<Route> routes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Route {
        private int id;
        private List<Integer> path;
        private List<String> carriages;
        private List<Schedule> schedule;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Builder
        public static class Schedule {
            private int rideId;
            private List<RideSegmentResponse> segments;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Station {
        private int stationId;
        private String city;
        private Geolocation geolocation;


        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Builder
        public static class Geolocation {
            private BigDecimal latitude;
            private BigDecimal longitude;
        }
    }
}
