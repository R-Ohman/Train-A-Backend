package pl.edu.pg.eti.train_a.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserDto {
    int id;
    String email;
    String name;
    String role;
}
