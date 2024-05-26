package id.ac.ui.cs.advprog.heymartstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(5)
                .build();
        products.add(product1);
    }

    @Test
    void testGetProductName() {
        assertEquals("Indomie", products.getFirst().getName());
    }

    @Test
    void testGetProductPrice() {
        assertEquals(10000L, products.getFirst().getPrice());
    }

    @Test
    void testGetProductStock() {
        assertEquals(5, products.getFirst().getStock());
    }

    @Test
    void testProductNullName() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName(null)
                    .setPrice(10000L)
                    .setStock(10)
                    .build();
        });
    }

    @Test
    void testNegativeStock() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName("Lala")
                    .setPrice(10000L)
                    .setStock(-1)
                    .build();
        });
    }

    @Test
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName("Lala")
                    .setPrice(-1L)
                    .setStock(1)
                    .build();
        });
    }

    @Test
    void testSupermarketNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName("Super")
                    .setPrice(10000L)
                    .setStock(10)
                    .setSupermarket(null)
                    .build();
        });
    }

    @Test
    void testProductEmptyName() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName("")
                    .setPrice(10000L)
                    .setStock(10)
                    .build();
        });
    }

    @Test
    void testProductMaliciousName() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new ProductBuilder()
                    .setName("ok' OR 1=1#")
                    .setPrice(10000L)
                    .setStock(10)
                    .build();
        });
    }
}
