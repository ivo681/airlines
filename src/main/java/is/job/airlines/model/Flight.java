package is.job.airlines.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity{
    private FlightRoute flightRoute;
    private Airplane airplane;
    private LocalDateTime departureDateAndTime;
    private LocalDateTime arrivalDateAndTime;
    private Set<Person> people = new HashSet<Person>();

    public Flight() {
    }

    @ManyToOne
    public FlightRoute getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(FlightRoute flightRoute) {
        this.flightRoute = flightRoute;
    }

    @ManyToOne
    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

//    @ManyToOne
//    public Airline getAirline() {
//        return airline;
//    }
//
//    public void setAirline(Airline airline) {
//        this.airline = airline;
//    }

    @Column(name = "departure_date_and_time", nullable = false)
    public LocalDateTime getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public void setDepartureDateAndTime(LocalDateTime departureDateAndTime) {
        this.departureDateAndTime = departureDateAndTime;
    }

    @Column(name = "arrival_date_and_time", nullable = false)
    public LocalDateTime getArrivalDateAndTime() {
        return arrivalDateAndTime;
    }

    public void setArrivalDateAndTime(LocalDateTime arrivalDateAndTime) {
        this.arrivalDateAndTime = arrivalDateAndTime;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "flights_people",
            joinColumns = @JoinColumn(name="flight_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="person_id", referencedColumnName = "id"))
    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
}
