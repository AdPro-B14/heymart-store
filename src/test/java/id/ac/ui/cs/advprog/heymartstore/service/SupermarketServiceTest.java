package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.EditSupermarketRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import id.ac.ui.cs.advprog.heymartstore.exception.ManagerAlreadyAddedException;
import id.ac.ui.cs.advprog.heymartstore.exception.ManagerRegistrationFailedException;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import id.ac.ui.cs.advprog.heymartstore.rest.AuthService;
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
    private SupermarketServiceImpl supermarketService;

    @Mock
    private AuthService authService;

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
        supermarket1.getManagers().add("williams@gmail.com");

        supermarketList.add(supermarket1);

        Supermarket supermarket2 = Supermarket.builder()
                .id(2L)
                .name("Indomaret Kukel")
                .managers(new ArrayList<>())
                .products(new ArrayList<>()).build();
        supermarket2.getManagers().add("arvinciu@gmail.com");

        supermarketList.add(supermarket2);
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

        supermarketService.deleteSupermarket(supermarketList.getFirst().getId());
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

        EditSupermarketRequest request = EditSupermarketRequest
                .builder()
                .name("Alfamart Kukel")
                .build();

        supermarketList.getFirst().setName("Alfamart Kukel");

        when(supermarketRepository.save(supermarketList.getFirst()))
                .thenReturn(supermarketList.getFirst());

        Supermarket supermarket = supermarketService.editSupermarket(supermarketList.getFirst().getId(),
                request);

        assertEquals(supermarket.getId(), supermarketList.getFirst().getId());
        assertEquals(supermarket.getName(), supermarketList.getFirst().getName());
    }

    @Test
    void testEditSupermarketNotValid() {
        EditSupermarketRequest request = EditSupermarketRequest.builder()
                .name("Alfamart Kukel")
                .build();

        assertThrows(NoSuchElementException.class, () -> supermarketService.editSupermarket(-1L,
                request));
        assertThrows(IllegalArgumentException.class, () -> supermarketService.editSupermarket(null,
                request));
        assertThrows(IllegalArgumentException.class, () -> supermarketService.editSupermarket(1L,
                null));
    }

    @Test
    void testGetAllSupermarketsValid() {
        when(supermarketRepository.findAll())
                .thenReturn(supermarketList);

        assertEquals(supermarketList.size(), supermarketService.getAllSupermarkets().size());
        for (int i=0; i<supermarketList.size(); i++) {
            assertEquals(supermarketList.get(i), supermarketService.getAllSupermarkets().get(i));
        }
    }

    @Test
    void testAddManagerValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));

        RegisterManagerRequest registerManagerRequest1 = RegisterManagerRequest
                .builder()
                .name("arvin")
                .email("arvin@gmail.com")
                .password("adpro123")
                .supermarketId(supermarketList.getFirst().getId())
                .adminToken("12345")
                .build();

        when(authService.registerManager(registerManagerRequest1)).thenReturn(true);

        supermarketService.addManager(1L, registerManagerRequest1);

        assertEquals(2, supermarketService.getSupermarket(1L).getManagers().size());
        assertEquals("arvin@gmail.com", supermarketService.getSupermarket(1L).getManagers().getLast());

        RegisterManagerRequest registerManagerRequest2 = RegisterManagerRequest
                .builder()
                .name("arvin123")
                .email("arvin@gmail.com")
                .password("adpro12553")
                .supermarketId(supermarketList.getFirst().getId())
                .adminToken("12345")
                .build();

        assertThrows(ManagerAlreadyAddedException.class, () -> supermarketService.addManager(1L,  registerManagerRequest2));
        assertEquals(2, supermarketService.getSupermarket(1L).getManagers().size());

        RegisterManagerRequest registerManagerRequest3 = RegisterManagerRequest
                .builder()
                .name("raissa")
                .email("raissa@gmail.com")
                .password("adpro")
                .supermarketId(supermarketList.getFirst().getId())
                .adminToken("12345")
                .build();

        when(authService.registerManager(registerManagerRequest3)).thenReturn(false);
        assertThrows(ManagerRegistrationFailedException.class, () -> supermarketService.addManager(1L,  registerManagerRequest3));
    }

    @Test
    void testRemoveManagerValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));
        when(authService.removeManager(any()))
                .thenReturn(true);

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());

        EditSupermarketRequest request = EditSupermarketRequest.builder()
                .adminToken("ABCDEF")
                .managers(new ArrayList<>())
                .build();

        supermarketService.editSupermarket(1L, request);

        assertEquals(0, supermarketService.getSupermarket(1L).getManagers().size());
    }

    @Test
    void testRemoveManagerNotValid() {
        when(supermarketRepository.findById(supermarketList.getFirst().getId()))
                .thenReturn(Optional.of(supermarketList.getFirst()));

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());

        assertThrows(IllegalArgumentException.class,
                () -> supermarketService.removeManager(1L, "213132", "raissa@gmail.com"));

        assertEquals(1, supermarketService.getSupermarket(1L).getManagers().size());

        List<String> modifiedManagers = new ArrayList<>(supermarketService.getSupermarket(1L).getManagers());
        modifiedManagers.add("awoeaokewoak@gmail.com");
        modifiedManagers.add("fowmafom@gmail.com");

        EditSupermarketRequest request = EditSupermarketRequest.builder()
                        .adminToken("ABCDEF")
                        .managers(modifiedManagers)
                        .build();

        assertThrows(IllegalArgumentException.class, () -> supermarketService.editSupermarket(1L, request));
    }
}