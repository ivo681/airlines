package is.job.airlines.service;

import is.job.airlines.model.service.AirportServiceModel;
import is.job.airlines.model.view.AirportViewModel;

import java.io.IOException;
import java.util.List;

public interface AirportService {
    boolean existingAirport(String airportName, String country);

    boolean findAirportById(String id);


    void saveAirport(AirportServiceModel airportServiceModel);

    List<AirportViewModel> getExistingAirports();

    void seedAirports() throws IOException;
}
