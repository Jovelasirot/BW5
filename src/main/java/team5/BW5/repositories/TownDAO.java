package team5.BW5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team5.BW5.entities.Town;

import java.util.Optional;

public interface TownDAO extends JpaRepository<Town, Long> {
    Optional<Town> findTownIdByTownName(String townName);
}
