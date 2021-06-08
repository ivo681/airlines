package is.job.airlines.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "airlines")
public class Airline extends BaseEntity{
    private String airlineName;
    private Long registrationNumber;

    public Airline() {
    }

    @Column(name = "airline_name", nullable = false, unique = true)
    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    @Column(name = "registration_number", unique = true, nullable = false)
    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
