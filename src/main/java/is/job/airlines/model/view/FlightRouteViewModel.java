package is.job.airlines.model.view;

public class FlightRouteViewModel {
    private String id;
    private String arrival_airport_id;
    private String departure_airport_id;
    private String arrival_description;
    private String departure_description;

    public FlightRouteViewModel() {
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

    public String getArrival_description() {
        return arrival_description;
    }

    public void setArrival_description(String arrival_description) {
        this.arrival_description = arrival_description;
    }

    public String getDeparture_description() {
        return departure_description;
    }

    public void setDeparture_description(String departure_description) {
        this.departure_description = departure_description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
