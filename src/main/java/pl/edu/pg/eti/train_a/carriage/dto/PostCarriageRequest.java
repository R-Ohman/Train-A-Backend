package pl.edu.pg.eti.train_a.carriage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostCarriageRequest {
    @NotBlank
    String name;
    @NotNull
    int rows;
    @NotNull
    int leftSeats;
    @NotNull
    int rightSeats;
}
