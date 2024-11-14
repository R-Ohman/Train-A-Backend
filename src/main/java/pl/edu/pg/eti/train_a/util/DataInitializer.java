package pl.edu.pg.eti.train_a.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.entity.*;
import pl.edu.pg.eti.train_a.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
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
    private final PriceRepository priceRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public DataInitializer(
            RouteRepository routeRepository,
            CarriageRepository carriageRepository,
            StationRepository stationRepository,
            RideRepository rideRepository,
            UserRepository userRepository,
            OrderRepository orderRepository,
            RailwayRepository railwayRepository,
            PriceRepository priceRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.routeRepository = routeRepository;
        this.carriageRepository = carriageRepository;
        this.stationRepository = stationRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.railwayRepository = railwayRepository;
        this.priceRepository = priceRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @PostConstruct
    public void initData() {
        if (carriageRepository.count() > 0) {
            return;
        }

        var carriages = List.of(
                Carriage.builder()
                        .code("car1")
                        .type("1st class")
                        .rows((byte) 25)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 1)
                        .build(),
                Carriage.builder()
                        .code("car2")
                        .type("2nd class")
                        .rows((byte) 30)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 2)
                        .build(),
                Carriage.builder()
                        .code("car3")
                        .type("3rd class")
                        .rows((byte) 35)
                        .leftSeats((byte) 2)
                        .rightSeats((byte) 3)
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
                Route.autoBuilder()
                        .carriages(carriages)
                        .stations(stations)
                        .build(),
                Route.autoBuilder()
                        .carriages(List.of(carriages.get(0), carriages.get(1), carriages.get(1)))
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build(),
                Route.autoBuilder()
                        .carriages(List.of(carriages.get(2), carriages.get(2), carriages.get(2)))
                        .stations(stations)
                        .build()
        );

        routeRepository.saveAll(routes);

        var rides = List.of(
                Ride.autoBuilder()
                        .route(routes.get(0))
                        .build(),
                Ride.autoBuilder()
                        .route(routes.get(0))
                        .build(),
                Ride.autoBuilder()
                        .route(routes.get(1))
                        .build()
        );

        rideRepository.saveAll(rides);

        var users = List.of(
                User.builder()
                        .email("user1@abc.com")
                        .name("User 1")
                        .role(UserRole.USER)
                        .passHash("pass1")
                        .build(),
                User.builder()
                        .email("admin1@adm.com")
                        .name("Admin 1")
                        .role(UserRole.MANAGER)
                        .passHash("pass2")
                        .build()
        );

        userRepository.saveAll(users);

        var orders = List.of(
                Order.autoBuilder()
                        .user(users.get(0))
                        .ride(rides.get(0))
                        .stationStart(rides.get(0).getRoute().getStations().get(0))
                        .stationEnd(rides.get(0).getRoute().getStations().get(1))
                        .seatId(11)
                        .status(OrderStatus.COMPLETED)
                        .build(),
                Order.autoBuilder()
                        .user(users.get(0))
                        .ride(rides.get(1))
                        .stationStart(rides.get(1).getRoute().getStations().get(0))
                        .stationEnd(rides.get(1).getRoute().getStations().get(2))
                        .seatId(22)
                        .status(OrderStatus.ACTIVE)
                        .build(),
                Order.autoBuilder()
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
                Railway.autoBuilder()
                        .stations(List.of(stations.get(0), stations.get(1)))
                        .build(),
                Railway.autoBuilder()
                        .stations(List.of(stations.get(1), stations.get(2)))
                        .build()
        );

        railwayRepository.saveAll(railways);

        var prices = List.of(
                Price.autoBuilder()
                        .ride(rides.get(0))
                        .carriage(carriages.get(0))
                        .railway(railways.get(0))
                        .price(BigDecimal.valueOf(100))
                        .build(),
                Price.autoBuilder()
                        .ride(rides.get(0))
                        .carriage(carriages.get(1))
                        .railway(railways.get(0))
                        .price(BigDecimal.valueOf(50))
                        .build(),
                Price.autoBuilder()
                        .ride(rides.get(0))
                        .carriage(carriages.get(0))
                        .railway(railways.get(1))
                        .price(BigDecimal.valueOf(25))
                        .build(),
                Price.autoBuilder()
                        .ride(rides.get(0))
                        .carriage(carriages.get(1))
                        .railway(railways.get(1))
                        .price(BigDecimal.valueOf(35))
                        .build(),
                Price.autoBuilder()
                        .ride(rides.get(1))
                        .carriage(carriages.get(1))
                        .railway(railways.get(1))
                        .price(BigDecimal.valueOf(75))
                        .build()
        );

        priceRepository.saveAll(prices);

        var schedules = List.of(
                Schedule.autoBuilder()
                        .ride(rides.get(0))
                        .railway(railways.get(0))
                        .departureTime(LocalDateTime.of(2024, Month.OCTOBER, 20, 10, 40))
                        .arrivalTime(LocalDateTime.of(2024, Month.OCTOBER, 20, 12, 30))
                        .build(),

                Schedule.autoBuilder()
                        .ride(rides.get(0))
                        .railway(railways.get(1))
                        .departureTime(LocalDateTime.of(2024, Month.OCTOBER, 20, 12, 55))
                        .arrivalTime(LocalDateTime.of(2024, Month.OCTOBER, 20, 15, 30))
                        .build(),

                Schedule.autoBuilder()
                        .ride(rides.get(1))
                        .railway(railways.get(0))
                        .departureTime(LocalDateTime.of(2024, Month.OCTOBER, 21, 11, 33))
                        .arrivalTime(LocalDateTime.of(2024, Month.OCTOBER, 21, 12, 23))
                        .build(),

                Schedule.autoBuilder()
                        .ride(rides.get(1))
                        .railway(railways.get(1))
                        .departureTime(LocalDateTime.of(2024, Month.OCTOBER, 21, 12, 51))
                        .arrivalTime(LocalDateTime.of(2024, Month.OCTOBER, 21, 14, 31))
                        .build(),

                Schedule.autoBuilder()
                        .ride(rides.get(2))
                        .railway(railways.get(0))
                        .departureTime(LocalDateTime.of(2025, Month.JANUARY, 2, 11, 40))
                        .arrivalTime(LocalDateTime.of(2025, Month.JANUARY, 2, 12, 30))
                        .build()
        );

        scheduleRepository.saveAll(schedules);
    }
}
