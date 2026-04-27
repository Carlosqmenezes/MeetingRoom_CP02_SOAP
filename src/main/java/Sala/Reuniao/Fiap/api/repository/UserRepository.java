package Sala.Reuniao.Fiap.api.repository;

import Sala.Reuniao.Fiap.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}