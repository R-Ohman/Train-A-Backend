package pl.edu.pg.eti.train_a.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostOrderRequest {
    @NotNull
    int rideId;
    @NotNull
    int seatId;
    @NotNull
    int stationStart;
    @NotNull
    int stationEnd;
}
