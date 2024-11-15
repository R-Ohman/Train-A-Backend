package pl.edu.pg.eti.train_a.station.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostStationRequest {
    String city;
    BigDecimal latitude;
    BigDecimal longitude;
    List<Integer> relations;
}
