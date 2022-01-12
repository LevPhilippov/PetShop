package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRole(String role);
}
