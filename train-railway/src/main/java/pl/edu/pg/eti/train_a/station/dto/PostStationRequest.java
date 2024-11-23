package pl.edu.pg.eti.train_a.station.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    String city;
    @NotNull
    BigDecimal latitude;
    @NotNull
    BigDecimal longitude;
    @NotEmpty
    List<@NotNull Integer> relations;
}
