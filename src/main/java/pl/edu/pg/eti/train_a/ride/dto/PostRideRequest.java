package pl.edu.pg.eti.train_a.ride.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostRideRequest {
    @NotEmpty
    private List<@NotNull Segment> segments;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Segment {
        @NotEmpty
        private List<@NotBlank String> time;
        @NotEmpty
        private Map<@NotBlank String, @NotNull BigDecimal> price;
    }
}
