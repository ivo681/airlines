package is.job.airlines.model.view;

import is.job.airlines.model.enums.ManufacturerEnum;

import java.math.BigDecimal;

public class AirplaneViewModel {
    private String id;
    private String manufacturer;
    private String model;
    private Long registrationNumber;
    private Long capacity;
    private BigDecimal parkingArea;
    private String airlineOwner;
    private String airlineUser;

    public AirplaneViewModel() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(BigDecimal parkingArea) {
        this.parkingArea = parkingArea;
    }

    public String getAirlineOwner() {
        return airlineOwner;
    }

    public void setAirlineOwner(String airlineOwner) {
        this.airlineOwner = airlineOwner;
    }

    public String getAirlineUser() {
        return airlineUser;
    }

    public void setAirlineUser(String airlineUser) {
        this.airlineUser = airlineUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
