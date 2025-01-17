package pl.edu.pg.eti.train_a.route.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostRouteRequest {
    @NotEmpty
    private List<@NotNull Integer> path;
    @NotEmpty
    private List<@NotBlank String> carriages;
}
