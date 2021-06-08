package is.job.airlines.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class AirportBindingModel {
    private String airportName;
    private String country;

    public AirportBindingModel() {
    }

    @NotBlank
    @Length(min = 3, message = "Airport name must be at least 3 characters")
    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    @NotBlank(message = "You must select a country to register an airport")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
