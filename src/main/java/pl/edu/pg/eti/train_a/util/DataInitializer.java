package pl.edu.pg.eti.train_a.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.entity.*;
import pl.edu.pg.eti.train_a.repository.*;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer {
    private final RouteRepository routeRepository;
    private final CarriageRepository carriageRepository;
    private final StationRepository stationRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RailwayRepository railwayRepository;

    @Autowired
    public DataInitializer(
            RouteRepository routeRepository,
            CarriageRepository carriageRepository,
            StationRepository stationRepository,
            RideRepository rideRepository,
            UserRepository userRepository,
            OrderRepository orderRepository,
            RailwayRepository railwayRepository
    ) {
        this.routeRepository = routeRepository;
        this.carriageRepository = carriageRepository;
        this.stationRepository = stationRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.railwayRepository = railwayRepository;
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

        var rides = List.of(
                Ride.autoBuilder()
                        .id(1)
                        .route(routes.get(0))
                        .build(),
                Ride.autoBuilder()
                        .id(2)
                        .route(routes.get(0))
                        .build(),
                Ride.autoBuilder()
                        .id(3)
                        .route(routes.get(1))
                        .build()
        );

        rideRepository.saveAll(rides);

        var users = List.of(
                User.builder()
                        .id(1)
                        .email("user1@abc.com")
                        .name("User 1")
                        .role("User")
                        .passHash("pass1")
                        .build(),
                User.builder()
                        .id(2)
                        .email("admin1@adm.com")
                        .name("Admin 1")
                        .role("Admin")
                        .passHash("pass2")
                        .build()
        );

        userRepository.saveAll(users);

        var orders = List.of(
                Order.autoBuilder()
                        .id(1)
                        .user(users.get(0))
                        .ride(rides.get(0))
                        .stationStart(rides.get(0).getRoute().getStations().get(0))
                        .stationEnd(rides.get(0).getRoute().getStations().get(1))
                        .seatId(1)
                        .status("confirmed")
                        .build(),
                Order.autoBuilder()
                        .id(2)
                        .user(users.get(0))
                        .ride(rides.get(1))
                        .stationStart(rides.get(1).getRoute().getStations().get(0))
                        .stationEnd(rides.get(1).getRoute().getStations().get(2))
                        .seatId(2)
                        .status("pending")
                        .build(),
                Order.autoBuilder()
                        .id(3)
                        .user(users.get(1))
                        .ride(rides.get(2))
                        .stationStart(rides.get(2).getRoute().getStations().get(0))
                        .stationEnd(rides.get(2).getRoute().getStations().get(1))
                        .seatId(3)
                        .status("cancelled")
                        .build()
        );

        orderRepository.saveAll(orders);

        var railways = List.of(
                Railway.autoBuilder()
                        .id(1)
                        .stations(List.of(stations.get(0), stations.get(1)))
                        .build(),
                Railway.autoBuilder()
                        .id(2)
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build()
        );

        railwayRepository.saveAll(railways);
    }
}
