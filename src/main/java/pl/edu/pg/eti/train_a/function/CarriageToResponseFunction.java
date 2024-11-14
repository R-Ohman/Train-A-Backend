package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.entity.Carriage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CarriageToResponseFunction implements Function<List<Carriage>, GetCarriagesResponse> {

    @Override
    public GetCarriagesResponse apply(List<Carriage> carriages) {
        return GetCarriagesResponse.builder()
                .carriages(carriages.stream()
                        .map(carriage -> GetCarriagesResponse.Carriage.builder()
                                .code(carriage.getCode())
                                .name(carriage.getType())
                                .rows(carriage.getRows())
                                .leftSeats(carriage.getLeftSeats())
                                .rightSeats(carriage.getRightSeats())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }
}
