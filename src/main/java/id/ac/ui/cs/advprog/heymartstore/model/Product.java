package id.ac.ui.cs.advprog.heymartstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "product")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "supermarket_id", nullable = false)
    @JsonIgnoreProperties(value = {"products", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Supermarket supermarket;

    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }
}
