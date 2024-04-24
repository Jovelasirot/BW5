package team5.BW5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team5.BW5.entities.Client;

import java.util.Optional;

public interface ClientDAO extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
