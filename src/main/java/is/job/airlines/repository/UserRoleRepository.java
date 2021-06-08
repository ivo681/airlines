package is.job.airlines.repository;

import is.job.airlines.model.UserRole;
import is.job.airlines.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(RoleEnum roleEnum);

}
