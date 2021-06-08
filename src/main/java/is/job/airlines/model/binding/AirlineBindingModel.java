package is.job.airlines.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AirlineBindingModel {
    private String airlineName;
    private Long registrationNumber;

    public AirlineBindingModel() {
    }

    @NotBlank
    @Length(min = 3, message = "Airline name must be at least 3 characters")
    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }


    @Min(value = 10000000, message = "Airline registration number must be exactly 8 digits")
    @Max(value = 99999999, message = "Airline registration number must be exactly 8 digits")
    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
