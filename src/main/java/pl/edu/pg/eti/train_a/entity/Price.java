package pl.edu.pg.eti.train_a.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

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

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", ride_id=" + ride.getId() +
                ", carriage_type=" + carriage.getType() +
                ", railway_id=" + railway.getId() +
                ", price=" + price +
                '}';
    }
}
