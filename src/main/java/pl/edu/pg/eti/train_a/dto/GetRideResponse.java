package pl.edu.pg.eti.train_a.dto;

import lombok.*;

import java.util.List;

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
    private List<Integer> path;
    private List<String> carriages;
    private Schedule schedule;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Schedule {
        private List<RideSegmentResponse> segments;
    }
}
