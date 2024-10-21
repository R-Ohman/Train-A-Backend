package pl.edu.pg.eti.train_a.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.StreamUtils;
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
                    stationService.findAllRailways().forEach(System.out::println);
                    break;
                case "orders":
                    orderService.findAll().forEach(System.out::println);
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

    private void showRoutes() {
        routeService.findAll().forEach(route -> {
            System.out.println("Route #" + route.getId());
            System.out.println("Carriages:");
            route.getCarriages().forEach(c -> System.out.printf("\t%s\n", c));
            System.out.println("Stations:");
            route.getStations().forEach(s -> System.out.printf("\t%s\n", s));
            System.out.println("Rides:");
            route.getRides().forEach(r -> System.out.printf("\tRide #%d\n", r.getId()));
            if (route.getRides().isEmpty()) {
                System.out.println("\tNo rides available");
            }
        });
    }

    private void showRides() {
        rideService.findAll().forEach(ride -> {
            System.out.println("Ride #" + ride.getId());
            System.out.printf("\tRoute #%s\n", ride.getRoute().getId());
            var routeStations = ride.getRoute().getStations();
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
}
