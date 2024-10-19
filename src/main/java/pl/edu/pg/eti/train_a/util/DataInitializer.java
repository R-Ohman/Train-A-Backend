package pl.edu.pg.eti.train_a.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.entity.Station;
import pl.edu.pg.eti.train_a.repository.CarriageRepository;
import pl.edu.pg.eti.train_a.repository.RouteRepository;
import pl.edu.pg.eti.train_a.repository.StationRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer {
    private final RouteRepository routeRepository;
    private final CarriageRepository carriageRepository;
    private final StationRepository stationRepository;

    @Autowired
    public DataInitializer(RouteRepository routeRepository, CarriageRepository carriageRepository, StationRepository stationRepository) {
        this.routeRepository = routeRepository;
        this.carriageRepository = carriageRepository;
        this.stationRepository = stationRepository;
    }

    @PostConstruct
    public void initData() {
        var carriages = List.of(
                Carriage.builder()
                        .id(1)
                        .type("1st class")
                        .rows((byte) 25)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 1)
                        .build(),
                Carriage.builder()
                        .id(2)
                        .type("2nd class")
                        .rows((byte) 30)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 2)
                        .build(),
                Carriage.builder()
                        .id(3)
                        .type("3rd class")
                        .rows((byte) 35)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 3)
                        .build()
        );

        var stations = List.of(
                Station.builder()
                        .id(1)
                        .city("Gdynia")
                        .latitude(BigDecimal.valueOf(54.5189))
                        .longitude(BigDecimal.valueOf(18.5305))
                        .build(),
                Station.builder()
                        .id(2)
                        .city("Gdańsk")
                        .latitude(BigDecimal.valueOf(54.3520))
                        .longitude(BigDecimal.valueOf(18.6466))
                        .build(),
                Station.builder()
                        .id(3)
                        .city("Sopot")
                        .latitude(BigDecimal.valueOf(54.4416))
                        .longitude(BigDecimal.valueOf(18.5601))
                        .build()
        );

        carriageRepository.saveAll(carriages);
        stationRepository.saveAll(stations);

        var routes = List.of(
                Route.autoBuilder()
                        .id(1)
                        .carriages(carriages)
                        .stations(stations)
                        .build(),
                Route.autoBuilder()
                        .id(2)
                        .carriages(List.of(carriages.get(0), carriages.get(1), carriages.get(1)))
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build(),
                Route.autoBuilder()
                        .id(3)
                        .carriages(List.of(carriages.get(2), carriages.get(2), carriages.get(2)))
                        .stations(stations)
                        .build()
        );

        routeRepository.saveAll(routes);
    }
}
