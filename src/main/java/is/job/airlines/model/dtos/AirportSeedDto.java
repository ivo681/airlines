package is.job.airlines.model.dtos;

import com.google.gson.annotations.Expose;

public class AirportSeedDto {
    @Expose
    private String airportName;

    public AirportSeedDto() {
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
