package pl.edu.pg.eti.train_a.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostCarriageRequest {
    String name;
    int rows;
    int leftSeats;
    int rightSeats;
}
