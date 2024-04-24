package id.ac.ui.cs.advprog.heymartstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "supermarket")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> managers;

    @Transient
    private List<Product> products;

    public void addProduct(Product product) {
    }

    public void removeProduct(Product newProduct) {
    }
}
