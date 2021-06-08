package is.job.airlines.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "airplanes")
public class Airplane extends BaseEntity{
    private String manufacturer;
    private String model;
    private Long registrationNumber;
    private Long capacity;
    private BigDecimal parkingArea;
    private Airline airlineOwner;
    private Airline airlineUser;

    public Airplane() {
    }

    @Column(name = "manufacturer", nullable = false)
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "registration_number", unique = true, nullable = false)
    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Column(name = "capacity", nullable = false)
    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @Column(name = "parking_area", nullable = false)
    public BigDecimal getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(BigDecimal parkingArea) {
        this.parkingArea = parkingArea;
    }

    @ManyToOne
    public Airline getAirlineOwner() {
        return airlineOwner;
    }

    public void setAirlineOwner(Airline airlineOwner) {
        this.airlineOwner = airlineOwner;
    }

    @ManyToOne
    public Airline getAirlineUser() {
        return airlineUser;
    }

    public void setAirlineUser(Airline airlineUser) {
        this.airlineUser = airlineUser;
    }
}
