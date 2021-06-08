package is.job.airlines.repository;

import is.job.airlines.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    @Query("SELECT a FROM Airport a WHERE a.airportName = :airportName AND a.country = :country")
    Optional<Airport> findByAirportNameAndCountry(String airportName, String country);
}
