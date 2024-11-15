package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.PostCarriageRequest;
import pl.edu.pg.eti.train_a.entity.Carriage;

import java.util.function.Function;

@Component
public class RequestToCarriageFunction implements Function<PostCarriageRequest, Carriage> {
    @Override
    public Carriage apply(PostCarriageRequest postCarriageRequest) {
        return Carriage.builder()
                .type(postCarriageRequest.getName())
                .rows(postCarriageRequest.getRows())
                .leftSeats(postCarriageRequest.getLeftSeats())
                .rightSeats(postCarriageRequest.getRightSeats())
                .build();
    }
}
