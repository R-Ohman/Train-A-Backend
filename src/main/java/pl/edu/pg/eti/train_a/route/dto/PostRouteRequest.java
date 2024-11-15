package pl.edu.pg.eti.train_a.route.dto;

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
    private List<Integer> path;
    private List<String> carriages;
}
