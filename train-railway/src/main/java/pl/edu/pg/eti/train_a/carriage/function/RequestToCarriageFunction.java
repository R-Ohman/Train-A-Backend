package pl.edu.pg.eti.train_a.carriage.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.carriage.dto.PostCarriageRequest;
import pl.edu.pg.eti.train_a.carriage.dto.PutCarriageRequest;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.UUID;
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

    public Carriage apply(UUID id, PutCarriageRequest putCarriageRequest) {
        return Carriage.builder()
                .code(id)
                .type(putCarriageRequest.getName())
                .rows(putCarriageRequest.getRows())
                .leftSeats(putCarriageRequest.getLeftSeats())
                .rightSeats(putCarriageRequest.getRightSeats())
                .build();
    }
}
