package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.dto.*;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.rest.UserService;
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
    private UserService userService;

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

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("manager@example.com")
                .name("Manager Name")
                .id(1L)
                .role("ADMIN")
                .build();

        when(userService.getProfile("validToken")).thenReturn(profileResponse);

        ResponseEntity<SuccessResponse> responseEntity = supermarketController.addManager(token, supermarketId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().success);
        verify(supermarketService, times(1)).addManager(anyLong(), any(RegisterManagerRequest.class));
    }

    @Test
    void testAddManager_Forbidden() {
        String token = "Bearer invalidToken";
        Long supermarketId = 1L;
        AddManagerRequest request = AddManagerRequest.builder()
                .name("Manager Name")
                .email("manager@example.com")
                .password("password")
                .build();

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("manager@example.com")
                .name("Manager Name")
                .id(1L)
                .role("USER")
                .build();

        when(userService.getProfile("invalidToken")).thenReturn(profileResponse);

        GetProfileResponse profileResponse2 = GetProfileResponse.builder()
                .email("manager@example.com")
                .name("Manager Name")
                .id(1L)
                .role("ADMIN")
                .build();

        when(userService.getProfile("invalidToken")).thenReturn(profileResponse2);

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.addManager(token, supermarketId, request);
        });

        verify(supermarketService, times(0)).addManager(anyLong(), any(RegisterManagerRequest.class));
    }

    @Test
    void testCreateSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        CreateSupermarketRequest request = new CreateSupermarketRequest();
        request.name = "New Supermarket";

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("manager@example.com")
                .name("Manager Name")
                .id(1L)
                .role("ADMIN")
                .build();

        when(userService.getProfile("validToken")).thenReturn(profileResponse);
        Supermarket createdSupermarket = new Supermarket();
        when(supermarketService.createSupermarket(request.name)).thenReturn(createdSupermarket);

        ResponseEntity<Supermarket> responseEntity = supermarketController.createSupermarket(token, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdSupermarket, responseEntity.getBody());
    }

    @Test
    void testCreateSupermarket_Forbidden() {
        String token = "Bearer invalidToken";
        CreateSupermarketRequest request = new CreateSupermarketRequest();

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("arvinciu86@gmail.com")
                .name("Arvin")
                .id(1L)
                .role("CUSTOMER")
                .build();

        when(userService.getProfile("invalidToken")).thenReturn(profileResponse);

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.createSupermarket(token, request);
        });

        verify(supermarketService, times(0)).createSupermarket(anyString());
    }

    @Test
    void testEditSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        Long supermarketId = 1L;
        EditSupermarketRequest request = new EditSupermarketRequest();
        request.name = "Updated Supermarket";

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("arvinciu86@gmail.com")
                .name("Arvin")
                .id(1L)
                .role("ADMIN")
                .build();

        when(userService.getProfile("validToken")).thenReturn(profileResponse);
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
        EditSupermarketRequest request = new EditSupermarketRequest();

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("arvinciu86@gmail.com")
                .name("Arvin")
                .id(1L)
                .role("CUSTOMER")
                .build();

        when(userService.getProfile("invalidToken")).thenReturn(profileResponse);

        assertThrows(IllegalAccessException.class, () -> {
            supermarketController.editSupermarket(token, supermarketId, request);
        });

        verify(supermarketService, times(0)).editSupermarket(anyLong(), any(EditSupermarketRequest.class));

        when(supermarketService.addManager(eq(supermarketId), any())).thenThrow(RuntimeException.class);

        assertThrows(Exception.class, () -> {
            supermarketController.editSupermarket(token, supermarketId, request);
        });
    }

    @Test
    void testDeleteSupermarket_Success() throws IllegalAccessException {
        String token = "Bearer validToken";
        Long supermarketId = 1L;

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("arvinciu86@gmail.com")
                .name("Arvin")
                .id(1L)
                .role("ADMIN")
                .build();

        when(userService.getProfile("validToken")).thenReturn(profileResponse);

        ResponseEntity<SuccessResponse> responseEntity = supermarketController.deleteSupermarket(token, supermarketId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().success);
    }

    @Test
    void testDeleteSupermarket_Forbidden() {
        String token = "Bearer invalidToken";
        Long supermarketId = 1L;

        GetProfileResponse profileResponse = GetProfileResponse.builder()
                .email("arvinciu86@gmail.com")
                .name("Arvin")
                .id(1L)
                .role("CUSTOMER")
                .build();

        when(userService.getProfile("invalidToken")).thenReturn(profileResponse);

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
