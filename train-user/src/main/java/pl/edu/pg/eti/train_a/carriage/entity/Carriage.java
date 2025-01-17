package pl.edu.pg.eti.train_a.carriage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carriages")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Carriage {
    @Id
    @Builder.Default
    @Column(name = "id")
    UUID code = UUID.randomUUID();

    @Column(unique = true)
    String type;
}
