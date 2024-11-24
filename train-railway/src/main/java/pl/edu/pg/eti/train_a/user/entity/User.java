package pl.edu.pg.eti.train_a.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    String username;

    String email;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @ToString.Exclude
    String passHash;
}
