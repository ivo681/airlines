package is.job.airlines.init;


import is.job.airlines.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final AirportService airportService;
    private final AirlineService airlineService;
    private final FlightRoutesService flightRoutesService;
    private final AirplaneService airplaneService;
    private final FlightsService flightsService;
    private final FlightCrewService flightCrewService;
    private final PassengerService passengerService;

    public DataInit(UserRoleService userRoleService, AirportService airportService, AirlineService airlineService, FlightRoutesService flightRoutesService, AirplaneService airplaneService, FlightsService flightsService, FlightCrewService flightCrewService, PassengerService passengerService) {
        this.userRoleService = userRoleService;
        this.airportService = airportService;
        this.airlineService = airlineService;
        this.flightRoutesService = flightRoutesService;
        this.airplaneService = airplaneService;
        this.flightsService = flightsService;
        this.flightCrewService = flightCrewService;
        this.passengerService = passengerService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedRoles();
        this.airportService.seedAirports();
        this.airlineService.seedAirlines();
        this.flightRoutesService.generateRoutes();
        this.airplaneService.seedAirplanes();
        this.flightCrewService.seedCrewCabin();
        this.passengerService.seedPassengers();
        this.flightsService.seedFlights();
    }
}
