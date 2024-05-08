package id.ac.ui.cs.advprog.heymartstore.repository;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByNameIsContainingIgnoreCase(String name);
}
