package id.ac.ui.cs.advprog.heymartstore.model;

import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SupermarketTest {
    @Test
    void testSupermarketBuild() {
        Supermarket supermarket = Supermarket.builder()
                .id(1)
                .name("Alfamart Kutek")
                .products(new ArrayList<Product>())
                .build();

        assertEquals(1, supermarket.getId());
        assertEquals("Alfamart Kutek", supermarket.getName());
        assertEquals(0, supermarket.getProducts().size());
    }
}
