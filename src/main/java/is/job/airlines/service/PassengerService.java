package is.job.airlines.service;

import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.model.view.PersonViewModel;

import java.io.IOException;
import java.util.List;

public interface PassengerService {
    boolean existingPassengerDetails(String email, String telephone);

    boolean existingPassenger(String idNumber);

    void savePassenger(PersonServiceModel personServiceModel);

    List<PersonViewModel> getExistingMembers();

    boolean isPassenger(String personId);

    boolean isPersonValid(String personId);

    void seedPassengers() throws IOException;
}
