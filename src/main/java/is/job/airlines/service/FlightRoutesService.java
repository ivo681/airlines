package is.job.airlines.service;

import is.job.airlines.model.service.FlightRouteServiceModel;
import is.job.airlines.model.view.FlightRouteViewModel;

import java.util.List;

public interface FlightRoutesService {
    boolean existingRoute(String departureAirportId, String arrivalAirportId);

    boolean existingRouteById(String id);

    void saveRoute(FlightRouteServiceModel flightRouteServiceModel);

    List<FlightRouteViewModel> getExistingRoutes();

    void generateRoutes();

}
