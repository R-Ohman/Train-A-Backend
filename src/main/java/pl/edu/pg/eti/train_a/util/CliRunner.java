package pl.edu.pg.eti.train_a.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;
import pl.edu.pg.eti.train_a.station.service.api.RailwayService;
import pl.edu.pg.eti.train_a.station.service.impl.RailwayServiceImpl;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

@Component
public class CliRunner implements CommandLineRunner {
    private final RouteService routeService;
    private final CarriageService carriageService;
    private final StationService stationService;
    private final RideService rideService;
    private final OrderService orderService;
    private final RailwayService railwayService;
    private final UserService userService;

    @Autowired
    public CliRunner(RouteService routeService, CarriageService carriageService, StationService stationService, RideService rideService, OrderService orderService, RailwayServiceImpl railwayService, UserService userService) {
        this.routeService = routeService;
        this.carriageService = carriageService;
        this.stationService = stationService;
        this.rideService = rideService;
        this.orderService = orderService;
        this.railwayService = railwayService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var isRunning = true;
        System.out.println("Welcome! Type \"help\" to list available commands.");
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
                carriageService.findAll().forEach(System.out::println);
                break;
            case "stations":
                stationService.findAll().forEach(System.out::println);
                break;
            case "railways":
                this.showRailways();
                break;
            case "orders":
                this.showOrders();
                break;
            case "users":
                userService.findAll().forEach(System.out::println);
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
                    routeService.delete(routeId);
                    System.out.println("Deleted route with ID: " + routeId);
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
                break;
            case "ride":
                System.out.print("Enter ride id: ");
                var rideId = Integer.parseInt(scanner.nextLine());
                try {
                    rideService.delete(rideId);
                    System.out.println("Deleted ride with ID: " + rideId);
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
                break;
            case "order":
                System.out.print("Enter order id: ");
                var orderId = Integer.parseInt(scanner.nextLine());
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
        routeService.findAll().stream().map(route -> routeService.findById(route.getId())).forEach(r -> {
            r.ifPresent(route -> {
                System.out.println("Route #" + route.getId());
                System.out.println("\tCarriages:");
                route.getCarriages().forEach(c -> System.out.printf("\t\t%s\n", c));
                System.out.println("\tStations:");
                route.getStations().forEach(s -> System.out.printf("\t\t%s\n", s));
                System.out.println("\tRides:");
                route.getRides().forEach(route_ride -> System.out.printf("\t\tRide #%d\n", route_ride.getId()));
                if (route.getRides().isEmpty()) {
                    System.out.println("\t\tNo rides available");
                }
            });
        });
    }

    private void showRides() {
        rideService.findAll()
                .stream().map(ride -> rideService.findById(ride.getId()))
                .forEach(r -> {
                    r.ifPresent(ride -> {
                        var route = routeService.findById(ride.getRoute().getId()).orElseThrow();
                        var routeStations = route.getStations();

                        System.out.println("Ride #" + ride.getId());
                        System.out.printf("\tRoute #%s\n", route.getId());

                        if (ride.getSegments().isEmpty()) {
                            System.out.println("\tSchedule is not provided yet!");
                            return;
                        }

                        for (int i = 0; i < ride.getSegments().size(); i++) {
                            var currentStation = routeStations.get(i);
                            var nextStation = routeStations.get(i + 1);
                            var segment = ride.getSegments().get(i);

                            System.out.printf("\t[%s - %s] From %s to %s\n\tPrice list:\n", segment.getDeparture(), segment.getArrival(), currentStation.getCity(), nextStation.getCity());

                            segment.getPrices().forEach((carriage, price) -> {
                                System.out.printf("\t\t%s - %.2f$\n", carriage.getType(), price);
                            });

                        }
                    });
                });
    }

    private void showOrders() {
        System.out.println("Orders:");
        orderService.findAll().forEach(order -> System.out.printf("id=%s\tuser_email=%s,\tride_id=%s,\tseat_id=%d,\tfrom=%s,\tto=%s,\tstatus=%s\n", order.getId(), order.getUser().getEmail(), order.getRide().getId(), order.getSeatId(), order.getStationStart().getCity(), order.getStationEnd().getCity(), order.getStatus()));
    }

    private void showRailways() {
        System.out.println("Railways:");
        railwayService.findAll().forEach(railway -> System.out.printf("\tstation1=%s,\tstation2=%s,\tdistance=%d\n", railway.getStations().get(0).getCity(), railway.getStations().get(1).getCity(), railway.getDistance()));
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

        var user = userService.findByEmail(userEmail).orElseThrow();
        var ride = rideService.findById(Integer.parseInt(rideId)).orElseThrow();
        var stationStart = stationService.findByCity(stationStartCity).orElseThrow();
        var stationEnd = stationService.findByCity(stationEndCity).orElseThrow();
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

        var carriages = carriageTypes.stream().map(String::trim).map(carriageService::findByType).map(Optional::orElseThrow).toList();
        var stations = stationIds.stream().map(String::trim).map(stationService::findByCity).map(Optional::orElseThrow).toList();

        var route = Route.autoBuilder().carriages(carriages).stations(stations).build();
        routeService.create(route);

        System.out.println("Created route with ID: " + route.getId());
    }

    @Transactional
    protected void addRide() {
        var scanner = new Scanner(System.in);
        System.out.print("Enter ride route ID: ");
        var routeId = Integer.parseInt(scanner.nextLine());
        var route = routeService.findById(routeId).orElseThrow();
        var ride = Ride.autoBuilder().route(route).build();
        rideService.create(ride);

        System.out.println("Created ride with ID: " + ride.getId());
    }
}
