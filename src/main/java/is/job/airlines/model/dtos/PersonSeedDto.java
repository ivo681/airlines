package is.job.airlines.model.dtos;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;

public class PersonSeedDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private LocalDate dateOfBirth;
    @Expose
    private String telephone;
    @Expose
    private String email;
    @Expose
    private String idNumber;
    @Expose
    private String crewJobPosition;

    public PersonSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCrewJobPosition() {
        return crewJobPosition;
    }

    public void setCrewJobPosition(String crewJobPosition) {
        this.crewJobPosition = crewJobPosition;
    }
}
