package pl.edu.pg.eti.train_a.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.order.entity.Order;

import java.util.ArrayList;
import java.util.List;
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

    String email;
    String name;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @ToString.Exclude
    String passHash;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();
}
