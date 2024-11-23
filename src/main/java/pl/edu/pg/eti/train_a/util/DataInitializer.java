package pl.edu.pg.eti.train_a.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.carriage.repository.api.CarriageRepository;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.order.entity.OrderStatus;
import pl.edu.pg.eti.train_a.order.repository.api.OrderRepository;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.entity.Segment;
import pl.edu.pg.eti.train_a.ride.repository.api.RideRepository;
import pl.edu.pg.eti.train_a.ride.repository.api.SegmentRepository;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.repository.api.RouteRepository;
import pl.edu.pg.eti.train_a.station.entity.Railway;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.repository.api.RailwayRepository;
import pl.edu.pg.eti.train_a.station.repository.api.StationRepository;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.entity.UserRole;
import pl.edu.pg.eti.train_a.user.repository.api.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer {
    private final RouteRepository routeRepository;
    private final CarriageRepository carriageRepository;
    private final StationRepository stationRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RailwayRepository railwayRepository;
    private final SegmentRepository segmentRepository;
    @Autowired
    public DataInitializer(
            RouteRepository routeRepository,
            CarriageRepository carriageRepository,
            StationRepository stationRepository,
            RideRepository rideRepository,
            UserRepository userRepository,
            OrderRepository orderRepository,
            RailwayRepository railwayRepository,
            SegmentRepository segmentRepository
    ) {
        this.routeRepository = routeRepository;
        this.carriageRepository = carriageRepository;
        this.stationRepository = stationRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.railwayRepository = railwayRepository;
        this.segmentRepository = segmentRepository;
    }

    @PostConstruct
    public void initData() {
        if (carriageRepository.count() > 0) {
            return;
        }

        var carriages = List.of(
                Carriage.builder()
                        .type("1st class")
                        .rows( 25)
                        .leftSeats( 2)
                        .rightSeats( 1)
                        .build(),
                Carriage.builder()
                        .type("2nd class")
                        .rows( 30)
                        .leftSeats( 2)
                        .rightSeats( 2)
                        .build(),
                Carriage.builder()
                        .type("3rd class")
                        .rows( 35)
                        .leftSeats( 2)
                        .rightSeats( 3)
                        .build()
        );

        var stations = List.of(
                Station.builder()
                        .city("Gdynia")
                        .latitude(BigDecimal.valueOf(54.5189))
                        .longitude(BigDecimal.valueOf(18.5305))
                        .build(),
                Station.builder()
                        .city("Gdańsk")
                        .latitude(BigDecimal.valueOf(54.3520))
                        .longitude(BigDecimal.valueOf(18.6466))
                        .build(),
                Station.builder()
                        .city("Sopot")
                        .latitude(BigDecimal.valueOf(54.4416))
                        .longitude(BigDecimal.valueOf(18.5601))
                        .build()
        );

        carriageRepository.saveAll(carriages);
        stationRepository.saveAll(stations);

        var routes = List.of(
                Route.builder()
                        .carriages(carriages)
                        .stations(stations)
                        .build(),
                Route.builder()
                        .carriages(List.of(carriages.get(0), carriages.get(1), carriages.get(1)))
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build(),
                Route.builder()
                        .carriages(List.of(carriages.get(2), carriages.get(2), carriages.get(2)))
                        .stations(stations)
                        .build()
        );

        routeRepository.saveAll(routes);

        var rides = List.of(
                Ride.builder()
                        .route(routes.get(0))
                        .build(),
                Ride.builder()
                        .route(routes.get(0))
                        .build(),
                Ride.builder()
                        .route(routes.get(1))
                        .build()
        );

        rideRepository.saveAll(rides);

        var segments = List.of(
                Segment.builder()
                        .ride(rides.get(0))
                        .departure(LocalDateTime.parse("2021-06-01T12:00:00"))
                        .arrival(LocalDateTime.parse("2021-06-01T13:00:00"))
                        .prices(Map.of(
                                carriages.get(0), BigDecimal.valueOf(100),
                                carriages.get(1), BigDecimal.valueOf(50),
                                carriages.get(2), BigDecimal.valueOf(25)
                        ))
                        .build(),
                Segment.builder()
                        .ride(rides.get(1))
                        .departure(LocalDateTime.parse("2025-06-01T14:00:00"))
                        .arrival(LocalDateTime.parse("2025-06-01T15:00:00"))
                        .prices(Map.of(
                                carriages.get(0), BigDecimal.valueOf(110),
                                carriages.get(1), BigDecimal.valueOf(55),
                                carriages.get(2), BigDecimal.valueOf(27.5)
                        ))
                        .build(),
                Segment.builder()
                        .ride(rides.get(1))
                        .departure(LocalDateTime.parse("2025-06-01T15:30:00"))
                        .arrival(LocalDateTime.parse("2025-06-01T16:30:00"))
                        .prices(Map.of(
                                carriages.get(0), BigDecimal.valueOf(120),
                                carriages.get(1), BigDecimal.valueOf(60),
                                carriages.get(2), BigDecimal.valueOf(30)
                        ))
                        .build(),
                Segment.builder()
                        .ride(rides.get(2))
                        .departure(LocalDateTime.parse("2022-06-01T12:00:00"))
                        .arrival(LocalDateTime.parse("2022-06-01T13:00:00"))
                        .prices(Map.of(
                                carriages.get(0), BigDecimal.valueOf(100),
                                carriages.get(1), BigDecimal.valueOf(50)
                        ))
                        .build()
        );

        segmentRepository.saveAll(segments);

        var users = List.of(
                User.builder()
                        .email("user@user.com")
                        .name("User 1")
                        .role(UserRole.USER)
                        .passHash("$2a$10$hHwnSpWQ7usdJ0B64Z8HKeK.F8rH/VOrYP0Egmiwf2UxnxNJv3DQW")
                        .build(),
                User.builder()
                        .email("admin@admin.com")
                        .name("Admin 1")
                        .role(UserRole.MANAGER)
                        .passHash("$2a$10$hHwnSpWQ7usdJ0B64Z8HKeK.F8rH/VOrYP0Egmiwf2UxnxNJv3DQW")
                        .build()
        );

        userRepository.saveAll(users);

        var orders = List.of(
                Order.builder()
                        .user(users.get(0))
                        .ride(rides.get(0))
                        .stationStart(rides.get(0).getRoute().getStations().get(0))
                        .stationEnd(rides.get(0).getRoute().getStations().get(1))
                        .seatId(11)
                        .status(OrderStatus.COMPLETED)
                        .build(),
                Order.builder()
                        .user(users.get(0))
                        .ride(rides.get(1))
                        .stationStart(rides.get(1).getRoute().getStations().get(0))
                        .stationEnd(rides.get(1).getRoute().getStations().get(2))
                        .seatId(22)
                        .status(OrderStatus.ACTIVE)
                        .build(),
                Order.builder()
                        .user(users.get(1))
                        .ride(rides.get(2))
                        .stationStart(rides.get(2).getRoute().getStations().get(0))
                        .stationEnd(rides.get(2).getRoute().getStations().get(1))
                        .seatId(33)
                        .status(OrderStatus.CANCELED)
                        .build()
        );

        orderRepository.saveAll(orders);

        var railways = List.of(
                Railway.builder()
                        .stations(List.of(stations.get(0), stations.get(1)))
                        .build(),
                Railway.builder()
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build()
        );

        railwayRepository.saveAll(railways);

    }
}
