package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.dto.*;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.service.JwtService;
import id.ac.ui.cs.advprog.heymartstore.service.SupermarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SupermarketControllerTest {

    @Mock
    private SupermarketService supermarketService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private SupermarketController supermarketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddManager_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        Long supermarketId = 1L;
        AddManagerRequest request = AddManagerRequest.builder()
                .name("Manager Name")
                .email("manager@example.com")
                .password("password")
                .build();

        when(jwtService.extractRole("validToken")).thenReturn("admin");

        ResponseEntity<SuccessResponse> responseEntity = supermarketController.addManager(token, supermarketId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().isSuccess());
        verify(supermarketService, times(1)).addManager(anyLong(), any(RegisterManagerRequest.class));
    }

    @Test
    void testAddManager_ForbiddenRole() {
        String token = "Bearer invalidToken";
        Long supermarketId = 1L;
        AddManagerRequest request = AddManagerRequest.builder()
                .name("Manager Name")
                .email("manager@example.com")
                .password("password")
                .build();

        when(jwtService.extractRole("invalidToken")).thenReturn("CUSTOMER");

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.addManager(token, supermarketId, request);
        });

        verify(supermarketService, times(0)).addManager(anyLong(), any(RegisterManagerRequest.class));
    }

    @Test
    void testCreateSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        CreateSupermarketRequest request = CreateSupermarketRequest
                .builder()
                .name("New Supermarket")
                .build();

        when(jwtService.extractRole("validToken")).thenReturn("admin");
        Supermarket createdSupermarket = new Supermarket();
        when(supermarketService.createSupermarket(token.replace("Bearer ", ""), request.getName())).thenReturn(createdSupermarket);

        ResponseEntity<Supermarket> responseEntity = supermarketController.createSupermarket(token, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdSupermarket, responseEntity.getBody());
    }

    @Test
    void testCreateSupermarket_Forbidden() {
        String token = "Bearer invalidToken";
        CreateSupermarketRequest request = CreateSupermarketRequest.builder().build();

        when(jwtService.extractRole("invalidToken")).thenReturn("customer");

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.createSupermarket(token, request);
        });

        verify(supermarketService, times(0)).createSupermarket(anyString(), anyString());
    }

    @Test
    void testEditSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        Long supermarketId = 1L;
        EditSupermarketRequest request = EditSupermarketRequest.builder()
                .name("Updated Supermarket")
                .build();

        when(jwtService.extractRole("validToken")).thenReturn("admin");

        Supermarket editedSupermarket = new Supermarket();
        when(supermarketService.editSupermarket(supermarketId, request)).thenReturn(editedSupermarket);

        ResponseEntity<Supermarket> responseEntity = supermarketController.editSupermarket(token, supermarketId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(editedSupermarket, responseEntity.getBody());
    }

    @Test
    void testEditSupermarket_Forbidden() {
        String token = "Bearer invalidToken";
        Long supermarketId = 1L;
        EditSupermarketRequest request = EditSupermarketRequest.builder().build();

        when(jwtService.extractRole("invalidToken")).thenReturn("customer");

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.editSupermarket(token, supermarketId, request);
        });

        verify(supermarketService, times(0)).editSupermarket(anyLong(), any(EditSupermarketRequest.class));
    }

    @Test
    void testDeleteSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        Long supermarketId = 1L;

        when(jwtService.extractRole("validToken")).thenReturn("admin");

        ResponseEntity<SuccessResponse> responseEntity = supermarketController.deleteSupermarket(token, supermarketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().isSuccess());
    }

    @Test
    void testDeleteSupermarket_Forbidden() {
        String token = "Bearer invalidToken";
        Long supermarketId = 1L;

        when(jwtService.extractRole("invalidToken")).thenReturn("customer");

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.deleteSupermarket(token, supermarketId);
        });
    }

    @Test
    void testGetAllSupermarkets() {
        List<Supermarket> supermarkets = Arrays.asList(new Supermarket(), new Supermarket());
        when(supermarketService.getAllSupermarkets()).thenReturn(supermarkets);

        ResponseEntity<List<Supermarket>> responseEntity = supermarketController.getAllSupermarkets();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(supermarkets, responseEntity.getBody());
    }

    @Test
    void testGetSupermarket() {
        Long supermarketId = 1L;
        Supermarket supermarket = new Supermarket();
        when(supermarketService.getSupermarket(supermarketId)).thenReturn(supermarket);

        ResponseEntity<Supermarket> responseEntity = supermarketController.getSupermarket(supermarketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(supermarket, responseEntity.getBody());
    }
}
