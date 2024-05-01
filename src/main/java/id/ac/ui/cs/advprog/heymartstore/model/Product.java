package id.ac.ui.cs.advprog.heymartstore.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supermarket_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Supermarket supermarket;

    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }
}
