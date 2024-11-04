package pl.edu.pg.eti.train_a.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetStationsResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Station {

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Builder
        public static class Connection {
            private UUID stationId;
            private int distance;
        }

        private UUID id;
        private String city;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private List<Connection> connectedTo;
    }

    private List<Station> stations;
}
