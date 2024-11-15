package pl.edu.pg.eti.train_a.ride.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RideSegmentResponse {
    private List<LocalDateTime> time;
    private Map<String, BigDecimal> price;
    private List<Integer> occupiedSeats;
}