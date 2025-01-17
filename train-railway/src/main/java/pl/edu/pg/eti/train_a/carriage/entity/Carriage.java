package pl.edu.pg.eti.train_a.carriage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.route.entity.Route;

import java.util.ArrayList;
import java.util.List;
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
    int rows;
    int leftSeats;
    int rightSeats;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_carriages",
            joinColumns = @JoinColumn(name = "carriage_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    @JsonIgnore
    List<Route> routes = new ArrayList<>();
}
