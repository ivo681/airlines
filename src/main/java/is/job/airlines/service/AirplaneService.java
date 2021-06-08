package is.job.airlines.service;

import is.job.airlines.model.service.AirplaneServiceModel;
import is.job.airlines.model.view.AirplaneViewModel;

import java.io.IOException;
import java.util.List;

public interface AirplaneService {
    boolean findAirplaneByRegistrationNumber(Long registrationNumber);

    boolean existingAirplaneById(String id);

    void saveAirplane(AirplaneServiceModel airplaneServiceModel);

    List<AirplaneViewModel> getExistingAirplanes();

    void seedAirplanes() throws IOException;

}
