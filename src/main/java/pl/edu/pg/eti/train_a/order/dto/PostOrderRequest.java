package pl.edu.pg.eti.train_a.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostOrderRequest {
    int rideId;
    int seatId;
    int stationStart;
    int stationEnd;
}
