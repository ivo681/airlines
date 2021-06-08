package is.job.airlines.model.binding;

import is.job.airlines.model.enums.ManufacturerEnum;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class AirplaneBindingModel {
    private ManufacturerEnum manufacturer;
    private String model;
    private Long registrationNumber;
    private Long capacity;
    private BigDecimal parkingArea;
    private String airlineOwnerIdNumber;
    private String airlineUserIdNumber;

    public AirplaneBindingModel() {
    }

    @Enumerated(EnumType.STRING)
    public ManufacturerEnum getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEnum manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NotBlank
    @Length(min = 3, message = "Airplane model must be at least 3 characters")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Min(value = 10000000, message = "Airplane registration number must be exactly 8 digits")
    @Max(value = 99999999, message = "Airplane registration number must be exactly 8 digits")
    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Min(value = 1, message = "Airplane capacity must be a positive number")
    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @Min(value = 1, message = "Airplane parking area must be a positive number")
    public BigDecimal getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(BigDecimal parkingArea) {
        this.parkingArea = parkingArea;
    }

    @NotBlank
    public String getAirlineOwnerIdNumber() {
        return airlineOwnerIdNumber;
    }

    public void setAirlineOwnerIdNumber(String airlineOwnerIdNumber) {
        this.airlineOwnerIdNumber = airlineOwnerIdNumber;
    }

    @NotBlank
    public String getAirlineUserIdNumber() {
        return airlineUserIdNumber;
    }

    public void setAirlineUserIdNumber(String airlineUserIdNumber) {
        this.airlineUserIdNumber = airlineUserIdNumber;
    }
}
