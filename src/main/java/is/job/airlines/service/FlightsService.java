package is.job.airlines.service;

import is.job.airlines.model.service.FlightServiceModel;
import is.job.airlines.model.view.FlightViewModel;
import is.job.airlines.model.view.PersonViewModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightsService {
    boolean existingFlightForRoute(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime);

    boolean occupiedAirplaneForDateTime(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime);

    void saveFlight(FlightServiceModel flightServiceModel);

    List<FlightViewModel> getExistingFlights();

    List<FlightViewModel> getUpcomingFlights();

    boolean availableCapacity(String flightId);

    boolean personIsOnFlight(String personId, String flightId);

    void getPersonOnFlight(String personId, String flightId);

    boolean isFlightValid(String flightId);

    List<PersonViewModel> getFlightList(String flightId);

    void seedFlights() throws IOException;

    List<FlightViewModel> getPersonFlightHistory(String personId);
}
