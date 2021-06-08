package is.job.airlines.model.binding;

import is.job.airlines.model.Airplane;
import is.job.airlines.model.FlightRoute;
import is.job.airlines.model.Person;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public class FlightBindingModel {
    private String flightRouteId;
    private String airplaneId;
    private LocalDateTime departureDateAndTime;
    private LocalDateTime arrivalDateAndTime;

    public FlightBindingModel() {
    }

    @NotBlank
    public String getFlightRouteId() {
        return flightRouteId;
    }

    public void setFlightRouteId(String flightRouteId) {
        this.flightRouteId = flightRouteId;
    }

    @NotBlank
    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    @NotNull(message = "Please select a date and time your departure")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "Departure date and time cannot be in the past")
    public LocalDateTime getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public void setDepartureDateAndTime(LocalDateTime departureDateAndTime) {
        this.departureDateAndTime = departureDateAndTime;
    }

    @NotNull(message = "Please select a date and time your arrival")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "Arrival date and time cannot be in the past")
    public LocalDateTime getArrivalDateAndTime() {
        return arrivalDateAndTime;
    }

    public void setArrivalDateAndTime(LocalDateTime arrivalDateAndTime) {
        this.arrivalDateAndTime = arrivalDateAndTime;
    }
}
