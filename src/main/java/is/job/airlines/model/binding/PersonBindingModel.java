package is.job.airlines.model.binding;

import is.job.airlines.model.Airline;
import is.job.airlines.model.Flight;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;


public class PersonBindingModel {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String telephone;
    private String email;
    private String idNumber;
    private Long crewIdNumber;
    private String crewJobPosition;
    private String employerName;
    private String employerId;

    public PersonBindingModel() {
    }

    @NotBlank
    @Length(min = 3, message = "First name must be at least 3 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Length(min = 3, message = "Last name must be at least 3 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth cannot be in the present or future")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @NotBlank(message = "Telephone field cannot be empty")
    @Pattern(regexp = "\\d{10,14}", message = "Please enter a valid 10 digit telephone number")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @NotBlank
    @Email(message = "Please enter a valid email address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "ID number field cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Please enter a valid 10 digit Id number")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getCrewIdNumber() {
        return crewIdNumber;
    }

    public void setCrewIdNumber(Long crewIdNumber) {
        this.crewIdNumber = crewIdNumber;
    }

    public String getCrewJobPosition() {
        return crewJobPosition;
    }

    public void setCrewJobPosition(String crewJobPosition) {
        this.crewJobPosition = crewJobPosition;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }
}
