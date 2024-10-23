package pl.edu.pg.eti.train_a.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.service.*;

import java.util.Scanner;

@Component
public class CliRunner implements CommandLineRunner {
    private final RouteService routeService;
    private final CarriageService carriageService;
    private final StationService stationService;
    private final RideService rideService;
    private final OrderService orderService;

    @Autowired
    public CliRunner(
            RouteService routeService,
            CarriageService carriageService,
            StationService stationService,
            RideService rideService,
            OrderService orderService
    ) {
        this.routeService = routeService;
        this.carriageService = carriageService;
        this.stationService = stationService;
        this.rideService = rideService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var isRunning = true;
        while (isRunning) {
            var command = scanner.nextLine();
            switch (command) {
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
                case "help":
                    System.out.println("Available commands: routes, rides, carriages, stations, railways, orders, help, exit");
                    break;
                case "exit":
                    isRunning = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }
    }

    protected void showRoutes() {
        routeService.findAll()
                .stream().map(route -> routeService.getRouteWithLazyProperties(route.getId()))
                .forEach(route -> {
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

    protected void showRides() {
        rideService.findAll()
                .stream().map(ride -> rideService.getRideWithLazyProperties(ride.getId()))
                .forEach(ride -> {
                    var route = routeService.getRouteWithLazyProperties(ride.getRoute().getId());
                    var routeStations = route.getStations();

                    System.out.println("Ride #" + ride.getId());
                    System.out.printf("\tRoute #%s\n", route.getId());

                    for (int i = 0; i < routeStations.size() - 1; i++) {
                        var currentStation = routeStations.get(i);
                        var nextStation = routeStations.get(i + 1);
                        var schedule = ride.getSchedules().get(i);
                        var departureTime = schedule.getDepartureTime();
                        var arrivalTime = schedule.getArrivalTime();

                        System.out.printf(
                                "\t[%s - %s] From %s to %s\n\tPrice list:\n",
                                departureTime, arrivalTime, currentStation.getCity(), nextStation.getCity()
                        );
                        ride.getPrices()
                                .stream().filter(price -> price.getRailway().equals(schedule.getRailway()))
                                .forEach(price -> {
                                    var carriageType = price.getCarriage().getType();
                                    var cost = price.getPrice();
                                    System.out.printf("\t\t%s - %.2f$\n", carriageType, cost);
                                });
                    }
                });
    }

    private void showOrders() {
        System.out.println("Orders:");
        orderService.findAll().forEach(order -> System.out.printf(
                        "\tuser_email=%s,\tride_id=%s,\tseat_id=%d,\tfrom=%s,\tto=%s,\tstatus=%s\n",
                        order.getUser().getEmail(), order.getRide().getId(), order.getSeatId(),
                        order.getStationStart().getCity(), order.getStationEnd().getCity(), order.getStatus()
                )
        );
    }

    private void showRailways() {
        System.out.println("Railways:");
        stationService.findAllRailways().forEach(railway ->
                System.out.printf(
                        "\tstation1=%s,\tstation2=%s,\tdistance=%d\n",
                        railway.getStations().get(0).getCity(), railway.getStations().get(1).getCity(), railway.getDistance()
                )
        );
    }
}
