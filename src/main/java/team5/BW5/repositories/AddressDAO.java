package team5.BW5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team5.BW5.entities.Address;

public interface AddressDAO extends JpaRepository<Address, Long> {
}
