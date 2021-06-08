package is.job.airlines.model.view;

import java.time.LocalDate;

public class PersonViewModel {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String telephone;
    private String email;
    private Long idNumber;
    private Long crewIdNumber;
    private String crewJobPosition;
    private String employerName;

    public PersonViewModel() {
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

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
