package team5.BW5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team5.BW5.entities.Invoice;

import java.util.Optional;

public interface InvoiceDAO extends JpaRepository<Invoice,Long> {
    Optional<Invoice>findById(long id);
}
