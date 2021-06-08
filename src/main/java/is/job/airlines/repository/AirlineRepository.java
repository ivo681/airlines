package is.job.airlines.repository;

import is.job.airlines.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {
    @Query("SELECT a FROM Airline a WHERE a.airlineName= :airlineName OR a.registrationNumber = :registrationNumber")
    Optional<Airline> findByAirlineNameAndRegistrationNumber(String airlineName, Long registrationNumber);

    @Query("SELECT a FROM Airline a WHERE a.registrationNumber = :registrationNumber")
    Optional<Airline> findByRegistrationNumber(Long registrationNumber);
}
