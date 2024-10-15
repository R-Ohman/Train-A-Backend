package pl.edu.pg.eti.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarriageDto implements Comparable<CarriageDto> {
    String type;

    int number;

    String train;

    @Override
    public int compareTo(CarriageDto o) {
        if (this.train.compareTo(o.train) != 0) {
            return this.train.compareTo(o.train);
        } else if (this.number != o.number) {
            return this.number - o.number;
        } else {
            return this.type.compareTo(o.type);
        }
    }
}
