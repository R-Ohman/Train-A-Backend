package pl.edu.pg.eti.train_a.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.service.CarriageService;
import pl.edu.pg.eti.train_a.service.RouteService;
import pl.edu.pg.eti.train_a.service.StationService;

import java.util.Scanner;

@Component
public class CliRunner implements CommandLineRunner {
    private final RouteService routeService;
    private final CarriageService carriageService;
    private final StationService stationService;

    @Autowired
    public CliRunner(RouteService routeService, CarriageService carriageService, StationService stationService) {
        this.routeService = routeService;
        this.carriageService = carriageService;
        this.stationService = stationService;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var isRunning = true;
        while (isRunning) {
            var command = scanner.nextLine();
            switch (command) {
                case "routes":
                    routeService.findAll().forEach(route -> {
                        System.out.println("Route #" + route.getId());
                        System.out.println("Carriages:");
                        route.getCarriages().forEach(c -> System.out.printf("\t%s\n", c));
                        System.out.println("Stations:");
                        route.getStations().forEach(s -> System.out.printf("\t%s\n", s));
                    });
                    break;
                case "carriages":
                    carriageService.findAll().forEach(System.out::println);
                    break;
                case "stations":
                    stationService.findAll().forEach(System.out::println);
                    break;
                case "help":
                    System.out.println("Available commands: routes, carriages, stations, help, exit");
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
}
