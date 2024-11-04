package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetUsersResponse;
import pl.edu.pg.eti.train_a.entity.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserToResponseFunction implements Function<List<User>, GetUsersResponse> {
        @Override
        public GetUsersResponse apply(List<User> users) {
            return GetUsersResponse.builder()
                    .users(users.stream()
                            .map(user -> GetUsersResponse.User.builder()
                                    .id(user.getId())
                                    .email(user.getEmail())
                                    .name(user.getName())
                                    .role(user.getRole())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    )
                    .build();
        }
}
