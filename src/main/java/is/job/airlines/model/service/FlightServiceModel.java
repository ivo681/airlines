package is.job.airlines.model.service;

import java.time.LocalDateTime;

public class FlightServiceModel {
    private String flightRouteId;
    private String airplaneId;
    private LocalDateTime departureDateAndTime;
    private LocalDateTime arrivalDateAndTime;

    public FlightServiceModel() {
    }

    public String getFlightRouteId() {
        return flightRouteId;
    }

    public void setFlightRouteId(String flightRouteId) {
        this.flightRouteId = flightRouteId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public LocalDateTime getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public void setDepartureDateAndTime(LocalDateTime departureDateAndTime) {
        this.departureDateAndTime = departureDateAndTime;
    }

    public LocalDateTime getArrivalDateAndTime() {
        return arrivalDateAndTime;
    }

    public void setArrivalDateAndTime(LocalDateTime arrivalDateAndTime) {
        this.arrivalDateAndTime = arrivalDateAndTime;
    }
}
