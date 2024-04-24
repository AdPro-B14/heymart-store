package id.ac.ui.cs.advprog.heymartstore.repository;

import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    Optional<Supermarket> findById(Long id);
}
