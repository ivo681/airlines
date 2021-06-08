package is.job.airlines.model.service;

import is.job.airlines.model.enums.ManufacturerEnum;

import java.math.BigDecimal;

public class AirplaneServiceModel {
    private ManufacturerEnum manufacturer;
    private String model;
    private Long registrationNumber;
    private Long capacity;
    private BigDecimal parkingArea;
    private String airlineOwnerIdNumber;
    private String airlineUserIdNumber;

    public AirplaneServiceModel() {
    }

    public ManufacturerEnum getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEnum manufacturer) {
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

    public String getAirlineOwnerIdNumber() {
        return airlineOwnerIdNumber;
    }

    public void setAirlineOwnerIdNumber(String airlineOwnerIdNumber) {
        this.airlineOwnerIdNumber = airlineOwnerIdNumber;
    }

    public String getAirlineUserIdNumber() {
        return airlineUserIdNumber;
    }

    public void setAirlineUserIdNumber(String airlineUserIdNumber) {
        this.airlineUserIdNumber = airlineUserIdNumber;
    }
}
