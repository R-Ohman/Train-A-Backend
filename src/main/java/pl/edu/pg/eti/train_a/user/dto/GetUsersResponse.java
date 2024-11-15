package pl.edu.pg.eti.train_a.user.dto;

import lombok.*;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetUsersResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class User {
        private int id;
        private String email;
        private String name;
        private UserRole role;
    }

    private List<User> users;
}
