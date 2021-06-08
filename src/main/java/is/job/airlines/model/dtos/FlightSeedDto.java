package is.job.airlines.model.dtos;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class FlightSeedDto {
    @Expose
    private String departureDateAndTime;

    public FlightSeedDto() {
    }

    public String getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public void setDepartureDateAndTime(String departureDateAndTime) {
        this.departureDateAndTime = departureDateAndTime;
    }
}
