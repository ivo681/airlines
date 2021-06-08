package is.job.airlines.service;

import is.job.airlines.model.service.AirlineServiceModel;
import is.job.airlines.model.view.AirlineViewModel;

import java.io.IOException;
import java.util.List;

public interface AirlineService {
    List<AirlineViewModel> getExistingAirlines();

    boolean existingAirline(String airlineName, Long registrationNumber);

    void saveAirline(AirlineServiceModel airlineServiceModel);

    boolean findAirlineById(String id);

    boolean findAirlineByRegistrationNumber(Long registrationNumber);

    void seedAirlines() throws IOException;
}
