package pl.edu.pg.eti.train_a.initialize;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.station.entity.Railway;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.service.api.RailwayService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InitializeData implements InitializingBean {
    private final StationService stationService;
    private final RailwayService railwayService;

    @Autowired
    public InitializeData(StationService stationService, RailwayService railwayService) {
        this.stationService = stationService;
        this.railwayService = railwayService;
    }

    @Override
    public void afterPropertiesSet() {
        if (stationService.findAll().isEmpty()) {
            stationService.create(Station.builder()
                    .city("Warszawa Centralna")
                    .latitude(BigDecimal.valueOf(52.2297))
                    .longitude(BigDecimal.valueOf(21.0122))
                    .build());
            stationService.create(Station.builder()
                    .city("Kraków Główny")
                    .latitude(BigDecimal.valueOf(50.0647))
                    .longitude(BigDecimal.valueOf(19.9450))
                    .build());
            stationService.create(Station.builder()
                    .city("Gdańsk Główny")
                    .latitude(BigDecimal.valueOf(54.3520))
                    .longitude(BigDecimal.valueOf(18.6466))
                    .build());
            stationService.create(Station.builder()
                    .city("Wrocław Główny")
                    .latitude(BigDecimal.valueOf(51.1079))
                    .longitude(BigDecimal.valueOf(17.0385))
                    .build());
            stationService.create(Station.builder()
                    .city("Poznań Główny")
                    .latitude(BigDecimal.valueOf(52.4064))
                    .longitude(BigDecimal.valueOf(16.9252))
                    .build());

            railwayService.create(Railway.builder()
                    .stations(List.of(
                            stationService.findByCity("Warszawa Centralna").orElseThrow(),
                            stationService.findByCity("Kraków Główny").orElseThrow()
                    ))
                    .build());
            railwayService.create(Railway.builder()
                    .stations(List.of(
                            stationService.findByCity("Gdańsk Główny").orElseThrow(),
                            stationService.findByCity("Warszawa Centralna").orElseThrow()
                    ))
                    .build());
            railwayService.create(Railway.builder()
                    .stations(List.of(
                            stationService.findByCity("Wrocław Główny").orElseThrow(),
                            stationService.findByCity("Warszawa Centralna").orElseThrow()
                    ))
                    .build());
            railwayService.create(Railway.builder()
                    .stations(List.of(
                            stationService.findByCity("Poznań Główny").orElseThrow(),
                            stationService.findByCity("Gdańsk Główny").orElseThrow()
                    ))
                    .build());
            railwayService.create(Railway.builder()
                    .stations(List.of(
                            stationService.findByCity("Kraków Główny").orElseThrow(),
                            stationService.findByCity("Poznań Główny").orElseThrow()
                    ))
                    .build());
        }
    }
}
