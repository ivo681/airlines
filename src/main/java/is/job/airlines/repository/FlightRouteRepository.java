package is.job.airlines.repository;

import is.job.airlines.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRouteRepository extends JpaRepository<FlightRoute, String> {

    @Query("SELECT fr FROM FlightRoute fr WHERE fr.departureAirport.id = :departureAirportId " +
            "AND fr.arrivalAirport.id = :arrivalAirportId")
    Optional<FlightRoute> existingRoute(String departureAirportId, String arrivalAirportId);
}
