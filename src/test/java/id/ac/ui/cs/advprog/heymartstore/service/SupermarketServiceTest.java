package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupermarketServiceTest {
    @InjectMocks
    private SupermarketService supermarketService;

    @Mock
    private SupermarketRepository supermarketRepository;

    @Mock
    private ProductRepository productRepository;

    List<Supermarket> supermarketList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Supermarket supermarket1 = Supermarket.builder()
                .id(1L)
                .name("Alfamart Kutek")
                .managers(new ArrayList<>())
                .products(new ArrayList<>()).build();
        supermarket1.getManagers().add("williams@gmail.com");

        supermarketList.add(supermarket1);
    }

    @Test
    void testCreateSupermarketValid() {
        when(supermarketRepository.save(any())).thenReturn(supermarketList.getFirst());
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));
        Supermarket supermarket = supermarketService.createSupermarket(supermarketList.getFirst().getName());

        assertEquals(supermarket.getName(), supermarketService.getSupermarket(supermarket.getId()).getName());
    }

    @Test
    void testCreateSupermarketNotValid() {
        assertThrows(IllegalArgumentException.class, () -> supermarketService.createSupermarket(null));
    }

    @Test
    void testDeleteSupermarketValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));

        Supermarket supermarket = supermarketService.deleteSupermarket(supermarketList.getFirst().getId());

        assertEquals(supermarket.getId(), supermarketList.getFirst().getId());
    }

    @Test
    void testDeleteSupermarketNotValid() {
        assertThrows(NoSuchElementException.class, () -> supermarketService.deleteSupermarket(-1L));
        assertThrows(IllegalArgumentException.class, () -> supermarketService.deleteSupermarket(null));
    }

    @Test
    void testEditSupermarketValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));

        supermarketList.getFirst().setName("Alfamart Kukel");

        Supermarket supermarket = supermarketService.editSupermarket(supermarketList.getFirst().getId(),
                supermarketList.getFirst());

        assertEquals(supermarket.getId(), supermarketList.getFirst().getId());
        assertEquals(supermarket.getName(), supermarketList.getFirst().getName());
    }

    @Test
    void testEditSupermarketNotValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));

        assertThrows(NoSuchElementException.class, () -> supermarketService.editSupermarket(-1L,
                supermarketList.getFirst()));
        assertThrows(IllegalArgumentException.class, () -> supermarketService.editSupermarket(null,
                supermarketList.getFirst()));
        assertThrows(IllegalArgumentException.class, () -> supermarketService.editSupermarket(1L,
                null));
    }

    @Test
    void testAddManagerValid() {
        for (Supermarket supermarket : supermarketList) {
            when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        }

        supermarketService.addManager(1L, "arvin@gmail.com");

        assertEquals(2, supermarketService.getSupermarket(1L).getManagers().size());
        assertEquals("arvin@gmail.com", supermarketService.getSupermarket(1L).getManagers().getLast());
    }

    @Test
    void testRemoveManagerValid() {
        for (Supermarket supermarket : supermarketList) {
            when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        }

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());

        supermarketService.removeManager(1L, "williams@gmail.com");

        assertEquals(0, supermarketService.getSupermarket(1L).getManagers().size());
    }

    @Test
    void testRemoveManagerNotValid() {
        for (Supermarket supermarket : supermarketList) {
            when(supermarketRepository.findById(supermarket.getId())).thenReturn(Optional.of(supermarket));
        }

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());

        assertThrows(IllegalArgumentException.class,
                () -> supermarketService.removeManager(1L, "raissa@gmail.com"));

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());
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
