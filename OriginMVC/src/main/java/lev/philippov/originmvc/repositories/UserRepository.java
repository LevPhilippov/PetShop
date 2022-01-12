package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhone(String phone);
}
