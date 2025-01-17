package pl.edu.pg.eti.train_a.station.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
            @JsonProperty("id")
            private int stationId;
            private int distance;
        }

        private int id;
        private String city;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private List<Connection> connectedTo;
    }

    private List<Station> stations;
}
