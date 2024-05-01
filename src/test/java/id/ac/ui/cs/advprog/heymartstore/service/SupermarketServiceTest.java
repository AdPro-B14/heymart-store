package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupermarketServiceTest {
    @InjectMocks
    private SupermarketService supermarketService;

    @Mock
    private SupermarketRepository supermarketRepository;

    List<Supermarket> supermarketList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Supermarket supermarket1 = Supermarket.builder()
                .id(1L)
                .name("Alfamart Kutek")
                .managers(new ArrayList<>())
                .products(new ArrayList<>()).build();

        supermarketList.add(supermarket1);
    }

    @Test
    void testAddManagerValid() {
        for (Supermarket supermarket : supermarketList) {
            when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        }

        supermarketService.addManager(1L, "arvin@gmail.com");

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());
        assertEquals("arvin@gmail.com", supermarketService.getSupermarket(1L).getManagers().getFirst());
    }

    @Test
    void testAddProductValid() {
        for (Supermarket supermarket : supermarketList) {
            when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        }

        Product product1 = Product.getBuilder().setName("Indomie Kuah Soto").setPrice(3500L).setStock(3).build();
        Product product2 = Product.getBuilder().setName("Indomie Kuah Goreng").setPrice(3000L).setStock(2).build();

        supermarketService.addProduct(1L, product1);
        supermarketService.addProduct(1L, product2);

        assertEquals(2, supermarketService.getSupermarket(1L).getProducts().size());
        assertEquals(product1, supermarketService.getSupermarket(1L).getProducts().getFirst());
        assertEquals(product2, supermarketService.getSupermarket(1L).getProducts().getLast());
    }

    @Test
    void testAddProductNotValid() {
        assertThrows(IllegalArgumentException.class, () -> supermarketService.addProduct(1L, null));
    }
}
