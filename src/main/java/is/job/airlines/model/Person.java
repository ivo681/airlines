package is.job.airlines.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
public class Person extends BaseEntity{
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String telephone;
    private String email;
    private String idNumber;
    private Long crewIdNumber;
    private String crewJobPosition;
    private Airline employer;
    private Set<Flight> flights = new HashSet<Flight>();

    public Person() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "date_of_birth", nullable = false)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "telephone", unique = true, nullable = false)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "id_number", unique = true, nullable = false)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Column(name = "crew_id_number", unique = true)
    public Long getCrewIdNumber() {
        return crewIdNumber;
    }

    public void setCrewIdNumber(Long crewIdNumber) {
        this.crewIdNumber = crewIdNumber;
    }

    @Column(name = "crew_job_position")
    public String getCrewJobPosition() {
        return crewJobPosition;
    }

    public void setCrewJobPosition(String crewJobPosition) {
        this.crewJobPosition = crewJobPosition;
    }

    @ManyToOne
    public Airline getEmployer() {
        return employer;
    }

    public void setEmployer(Airline employer) {
        this.employer = employer;
    }

    @ManyToMany(mappedBy = "people", targetEntity = Flight.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }
}
