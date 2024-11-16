package pl.edu.pg.eti.train_a.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PutUserRequest {
    private String email;
    private String name;
}
