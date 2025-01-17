package pl.edu.pg.eti.train_a.user.dto;

import lombok.*;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserProfileResponse {
    private String email;
    private String name;
    private UserRole role;
}
