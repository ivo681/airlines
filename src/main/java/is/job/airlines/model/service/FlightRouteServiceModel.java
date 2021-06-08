package is.job.airlines.model.service;

public class FlightRouteServiceModel {
    private String arrival_airport_id;
    private String departure_airport_id;

    public FlightRouteServiceModel() {
    }

    public String getArrival_airport_id() {
        return arrival_airport_id;
    }

    public void setArrival_airport_id(String arrival_airport_id) {
        this.arrival_airport_id = arrival_airport_id;
    }

    public String getDeparture_airport_id() {
        return departure_airport_id;
    }

    public void setDeparture_airport_id(String departure_airport_id) {
        this.departure_airport_id = departure_airport_id;
    }
}
