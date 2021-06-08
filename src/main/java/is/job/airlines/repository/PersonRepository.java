package is.job.airlines.repository;

import is.job.airlines.model.Person;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Query("SELECT p FROM Person p WHERE p.email = :email OR p.telephone= :telephone")
    Optional<Person> existingDetails(String email, String telephone);

    @Query("SELECT p FROM Person p WHERE p.idNumber = :idNumber")
    Optional<Person> existingPersonById(String idNumber);

    @Query("SELECT p FROM Person p WHERE p.crewIdNumber = :idNumber")
    Optional<Person> findByCrewIdNumber(Long idNumber);

    @Query("SELECT p FROM Person p WHERE p.crewIdNumber IS NOT NULL")
    List<Person> findAllCrewMembers();

    @Query("SELECT p FROM Person p WHERE p.crewIdNumber IS NULL")
    List<Person> findAllPassengers();

    @Query("SELECT p FROM Person p WHERE p.id = :personId AND p.crewIdNumber IS NULL")
    Optional<Person> isPassengerById(String personId);
}
