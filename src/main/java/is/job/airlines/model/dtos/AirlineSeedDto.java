package is.job.airlines.model.dtos;

import com.google.gson.annotations.Expose;

public class AirlineSeedDto {
    @Expose
    private String airlineName;

    public AirlineSeedDto() {
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
