package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Price {
    @Id
    @ToString.Exclude
    @Builder.Default
    @Column(name = "id")
    UUID id = UUID.randomUUID();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ride_id")
    Ride ride;

    @ManyToOne
    @JoinColumn(name = "carriage_type")
    Carriage carriage;

    @ManyToOne
    @JoinColumn(name = "railway_id")
    Railway railway;

    BigDecimal price;

    public static PriceBuilder autoBuilder() {
        return new AutoPriceBuilder();
    }

    public static class AutoPriceBuilder extends PriceBuilder {
        @Override
        public Price build() {
            var price = super.build();
            price.getRailway().getPrices().add(price);
            price.getCarriage().getPrices().add(price);
            price.getRide().getPrices().add(price);
            return price;
        }
    }
}
