package pl.edu.pg.eti.train_a.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Order;
import pl.edu.pg.eti.train_a.entity.Ride;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.service.carriage.CarriageServiceImpl;
import pl.edu.pg.eti.train_a.service.order.OrderService;
import pl.edu.pg.eti.train_a.service.railway.RailwayServiceImpl;
import pl.edu.pg.eti.train_a.service.ride.RideServiceImpl;
import pl.edu.pg.eti.train_a.service.route.RouteServiceImpl;
import pl.edu.pg.eti.train_a.service.station.StationServiceImpl;
import pl.edu.pg.eti.train_a.service.user.UserServiceImpl;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

@Component
public class CliRunner implements CommandLineRunner {
    private final RouteServiceImpl routeServiceImpl;
    private final CarriageServiceImpl carriageServiceImpl;
    private final StationServiceImpl stationServiceImpl;
    private final RideServiceImpl rideServiceImpl;
    private final OrderService orderService;
    private final RailwayServiceImpl railwayServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public CliRunner(RouteServiceImpl routeServiceImpl, CarriageServiceImpl carriageServiceImpl, StationServiceImpl stationServiceImpl, RideServiceImpl rideServiceImpl, OrderService orderService, RailwayServiceImpl railwayServiceImpl, UserServiceImpl userServiceImpl) {
        this.routeServiceImpl = routeServiceImpl;
        this.carriageServiceImpl = carriageServiceImpl;
        this.stationServiceImpl = stationServiceImpl;
        this.rideServiceImpl = rideServiceImpl;
        this.orderService = orderService;
        this.railwayServiceImpl = railwayServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var isRunning = true;
        while (isRunning) {
            System.out.print("Enter command: ");
            var input = scanner.nextLine().split(" ");
            var command = input[0];
            var target = input.length > 1 ? input[1] : "";

            switch (command) {
                case "ls":
                    this.list(target);
                    break;
                case "rm":
                    this.remove(target);
                    break;
                case "add":
                    this.add(target);
                    break;
                case "help":
                    System.out.println("Available commands: ls [target #1], rm [target #2], add [target #2], help, exit.");
                    System.out.println("\tTargets #1: routes, rides, carriages, stations, railways, orders, users.");
                    System.out.println("\tTargets #2: route, ride, order.");
                    break;
                case "exit":
                    isRunning = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Unknown command: " + command + ". Type 'help' for available commands.");
            }
        }
    }

    private void list(String target) {
        switch (target) {
            case "routes":
                this.showRoutes();
                break;
            case "rides":
                this.showRides();
                break;
            case "carriages":
                carriageServiceImpl.findAll().forEach(System.out::println);
                break;
            case "stations":
                stationServiceImpl.findAll().forEach(System.out::println);
                break;
            case "railways":
                this.showRailways();
                break;
            case "orders":
                this.showOrders();
                break;
            case "users":
                userServiceImpl.findAll().forEach(System.out::println);
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    private void remove(String target) {
        var scanner = new Scanner(System.in);
        switch (target) {
            case "route":
                System.out.print("Enter route id: ");
                var routeId = Integer.parseInt(scanner.nextLine());
                try {
                    routeServiceImpl.delete(routeId);
                    System.out.println("Deleted route with ID: " + routeId);
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
                break;
            case "ride":
                System.out.print("Enter ride id: ");
                var rideId = Integer.parseInt(scanner.nextLine());
                try {
                    rideServiceImpl.delete(rideId);
                    System.out.println("Deleted ride with ID: " + rideId);
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
                break;
            case "order":
                System.out.print("Enter order id: ");
                var orderId = UUID.fromString(scanner.nextLine());
                try {
                    orderService.delete(orderId);
                    System.out.println("Deleted order with ID: " + orderId);
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    private void add(String target) {
        switch (target) {
            case "route":
                this.addRoute();
                break;
            case "ride":
                this.addRide();
                break;
            case "order":
                this.addOrder();
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    private void showRoutes() {
        routeServiceImpl.findAll().stream().map(route -> routeServiceImpl.findByIdWithDetails(route.getId())).forEach(route -> {
            System.out.println("Route #" + route.getId());
            System.out.println("\tCarriages:");
            route.getCarriages().forEach(c -> System.out.printf("\t\t%s\n", c));
            System.out.println("\tStations:");
            route.getStations().forEach(s -> System.out.printf("\t\t%s\n", s));
            System.out.println("\tRides:");
            route.getRides().forEach(r -> System.out.printf("\t\tRide #%d\n", r.getId()));
            if (route.getRides().isEmpty()) {
                System.out.println("\t\tNo rides available");
            }
        });
    }

    private void showRides() {
        rideServiceImpl.findAll().stream().map(ride -> rideServiceImpl.findByIdWithDetails(ride.getId())).forEach(ride -> {
            var route = routeServiceImpl.findByIdWithDetails(ride.getRoute().getId());
            var routeStations = route.getStations();

            System.out.println("Ride #" + ride.getId());
            System.out.printf("\tRoute #%s\n", route.getId());

            if (ride.getSchedules().isEmpty() || ride.getPrices().isEmpty()) {
                System.out.println("\tNo schedules or prices available");
                return;
            }

            for (int i = 0; i < routeStations.size() - 1; i++) {
                var currentStation = routeStations.get(i);
                var nextStation = routeStations.get(i + 1);
                var schedule = ride.getSchedules().get(i);
                var departureTime = schedule.getDepartureTime();
                var arrivalTime = schedule.getArrivalTime();

                System.out.printf("\t[%s - %s] From %s to %s\n\tPrice list:\n", departureTime, arrivalTime, currentStation.getCity(), nextStation.getCity());
                var prices = ride.getPrices().stream()
                        .filter(price -> price.getRailway().equals(schedule.getRailway()))
                        .toList();

                if (prices.isEmpty()) {
                    System.out.println("\t\tNo prices available");
                } else {
                    prices.forEach(price -> {
                        var carriageType = price.getCarriage().getType();
                        var cost = price.getPrice();
                        System.out.printf("\t\t%s - %.2f$\n", carriageType, cost);
                    });
                }
            }
        });
    }

    private void showOrders() {
        System.out.println("Orders:");
        orderService.findAll().forEach(order -> System.out.printf("id=%s\tuser_email=%s,\tride_id=%s,\tseat_id=%d,\tfrom=%s,\tto=%s,\tstatus=%s\n", order.getId(), order.getUser().getEmail(), order.getRide().getId(), order.getSeatId(), order.getStationStart().getCity(), order.getStationEnd().getCity(), order.getStatus()));
    }

    private void showRailways() {
        System.out.println("Railways:");
        railwayServiceImpl.findAll().forEach(railway -> System.out.printf("\tstation1=%s,\tstation2=%s,\tdistance=%d\n", railway.getStations().get(0).getCity(), railway.getStations().get(1).getCity(), railway.getDistance()));
    }

    @Transactional
    protected void addOrder() {
        var scanner = new Scanner(System.in);
        System.out.print("User email: ");
        var userEmail = scanner.nextLine();
        System.out.print("Ride ID: ");
        var rideId = scanner.nextLine();
        System.out.print("Seat ID: ");
        var seatId = scanner.nextLine();
        System.out.print("Start station: ");
        var stationStartCity = scanner.nextLine();
        System.out.print("End station: ");
        var stationEndCity = scanner.nextLine();

        var user = userServiceImpl.findByEmailWithDetails(userEmail);
        var ride = rideServiceImpl.findByIdWithDetails(Integer.parseInt(rideId));
        var stationStart = stationServiceImpl.findByCityWithDetails(stationStartCity);
        var stationEnd = stationServiceImpl.findByCityWithDetails(stationEndCity);
        var order = Order.autoBuilder().user(user).ride(ride).seatId(Integer.parseInt(seatId)).stationStart(stationStart).stationEnd(stationEnd).build();
        orderService.create(order);

        System.out.println("Created order with ID: " + order.getId());
    }

    @Transactional
    public void addRoute() {
        var scanner = new Scanner(System.in);
        System.out.print("Enter route carriage types (separate by comma): ");
        var carriageTypes = Arrays.asList(scanner.nextLine().split(","));
        System.out.print("Enter route stations (separate by comma): ");
        var stationIds = Arrays.asList(scanner.nextLine().split(","));

        var carriages = carriageTypes.stream().map(String::trim).map(carriageServiceImpl::findByTypeWithDetails).toList();
        var stations = stationIds.stream().map(String::trim).map(stationServiceImpl::findByCityWithDetails).toList();

        var route = Route.autoBuilder().carriages(carriages).stations(stations).build();
        routeServiceImpl.create(route);

        System.out.println("Created route with ID: " + route.getId());
    }

    @Transactional
    protected void addRide() {
        var scanner = new Scanner(System.in);
        System.out.print("Enter ride route ID: ");
        var routeId = Integer.parseInt(scanner.nextLine());
        var route = routeServiceImpl.findByIdWithDetails(routeId);
        var ride = Ride.autoBuilder().route(route).build();
        rideServiceImpl.create(ride);

        System.out.println("Created ride with ID: " + ride.getId());
    }
}
