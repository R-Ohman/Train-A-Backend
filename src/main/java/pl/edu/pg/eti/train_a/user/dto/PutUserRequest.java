package pl.edu.pg.eti.train_a.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PutUserRequest {
    @NotBlank
    @Email(message = "Email is wrong")
    private String email;
    @NotBlank
    private String name;
}
