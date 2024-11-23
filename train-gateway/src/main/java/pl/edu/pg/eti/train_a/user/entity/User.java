package pl.edu.pg.eti.train_a.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
 import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Builder.Default
    String username = String.valueOf(UUID.randomUUID());

    @Enumerated(EnumType.STRING)
    UserRole role;

    @ToString.Exclude
    String passHash;
}
