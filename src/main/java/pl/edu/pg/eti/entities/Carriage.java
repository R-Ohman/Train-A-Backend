package pl.edu.pg.eti.entities;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(buildMethodName = "buildInternal")
public class Carriage implements Serializable {
    String type;

    int number;

    Train train;

    public static class CarriageBuilder {
        public Carriage build() {
            var carriage = this.buildInternal();
            carriage.getTrain().getCarriages().add(carriage);
            return carriage;
        }
    }
}
