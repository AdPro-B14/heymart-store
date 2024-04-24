package id.ac.ui.cs.advprog.heymartstore.repository;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p.name FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Product> searchProduct(@Param("name") String name);
}
