package is.job.airlines.model.dtos;

import com.google.gson.annotations.Expose;
import is.job.airlines.model.enums.ManufacturerEnum;

import java.math.BigDecimal;

public class AirplaneSeedDto {
    @Expose
    private String manufacturer;
    @Expose
    private String model;
    @Expose
    private Long registrationNumber;
    @Expose
    private Long capacity;
    @Expose
    private BigDecimal parkingArea;

    public AirplaneSeedDto() {
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
}
