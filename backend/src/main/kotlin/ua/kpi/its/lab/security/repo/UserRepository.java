package ua.kpi.its.lab.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.its.lab.security.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
