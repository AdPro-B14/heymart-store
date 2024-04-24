package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import org.apache.catalina.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupermarketServiceTest {
    @InjectMocks
    private SupermarketService supermarketService;

    @Mock
    private SupermarketRepository supermarketRepository;

    @BeforeEach
    void setUp() {
        Supermarket supermarket1 = Supermarket.builder()
                .id(1L)
                .name("Alfamart Kutek")
                .managers(new ArrayList<String>())
                .build();

        when(supermarketRepository.findById(supermarket1.getId())).thenReturn(Optional.of(supermarket1));
    }
    @Test
    void testAddManagerValid() {
        supermarketService.addManager("arvin@gmail.com");

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());
        assertEquals("arvin@gmail.com", supermarketService.getSupermarket(1L).getManagers().getFirst());
    }
}
