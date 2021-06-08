package is.job.airlines.service;


import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.model.view.PersonViewModel;

import java.io.IOException;
import java.util.List;

public interface FlightCrewService {
    boolean existingCrewMemberDetails(String email, String telephone);

    boolean existingCrewMember(String idNumber);

    void saveCrewMember(PersonServiceModel personServiceModel);

    boolean isCrewIdNumberTaken(Long idNumber);

    List<PersonViewModel> getExistingMembers();

    void seedCrewCabin() throws IOException;
}
