package is.job.airlines.repository;

import is.job.airlines.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    @Query("SELECT f FROM Flight f WHERE f.airplane.id=:airplaneId AND f.flightRoute.id = :flightRouteId " +
            "AND f.departureDateAndTime = :departureDateAndTime")
    Optional<Flight> existingFlightForRoute(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime);

    @Query("SELECT f FROM Flight f WHERE f.airplane.id=:airplaneId AND f.flightRoute.id <> :flightRouteId " +
            "AND f.departureDateAndTime = :departureDateAndTime")
    Optional<Flight> occupiedAirplaneForDateTime(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime);

    @Query("SELECT f FROM Flight f WHERE f.departureDateAndTime > :now")
    List<Flight> findUpcomingFlights(LocalDateTime now);

}
