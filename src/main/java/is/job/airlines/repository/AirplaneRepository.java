package is.job.airlines.repository;

import is.job.airlines.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, String> {

    Optional<Airplane> findByRegistrationNumber(Long registrationNumber);
}
