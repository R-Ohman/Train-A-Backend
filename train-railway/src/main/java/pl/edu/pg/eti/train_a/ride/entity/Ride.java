package pl.edu.pg.eti.train_a.ride.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.route.entity.Route;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rides")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    Route route;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Segment> segments = new ArrayList<>();

    @PreRemove
    @JsonIgnore
    private void checkForOrdersBeforeRemove() {
        if (!orders.isEmpty()) {
            throw new IllegalStateException("Cannot delete Ride " + id + " as it has associated Orders.");
        }
    }
}
