package is.job.airlines.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport extends BaseEntity{
    private String airportName;
    private String country;

    public Airport() {
    }

    @Column(name = "airport_name", nullable = false)
    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
