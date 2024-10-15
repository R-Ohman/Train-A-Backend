package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String city;
    BigDecimal latitude;
    BigDecimal longitude;
}
