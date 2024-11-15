package pl.edu.pg.eti.train_a.carriage.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetCarriagesResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Carriage {
        private String code;
        private String name;
        private int rows;
        private int leftSeats;
        private int rightSeats;
    }

    private List<Carriage> carriages;
}
