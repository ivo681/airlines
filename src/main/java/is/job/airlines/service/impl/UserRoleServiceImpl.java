package is.job.airlines.service.impl;

import is.job.airlines.model.UserRole;
import is.job.airlines.model.enums.RoleEnum;
import is.job.airlines.repository.UserRoleRepository;
import is.job.airlines.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedRoles() {
        if (this.userRoleRepository.count() == 0){
            Arrays.stream(RoleEnum.values()).forEach(e -> {
                UserRole userRole = new UserRole();
                userRole.setRole(e);
                this.userRoleRepository.save(userRole);
            });
        }
    }
}
