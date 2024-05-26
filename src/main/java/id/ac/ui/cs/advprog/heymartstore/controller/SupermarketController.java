package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.dto.*;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.rest.UserService;
import id.ac.ui.cs.advprog.heymartstore.service.SupermarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supermarket")
@RequiredArgsConstructor
public class SupermarketController {
    private final SupermarketService supermarketService;
    private final UserService userService;

    @PutMapping("/add-manager/{id}")
    public ResponseEntity<SuccessResponse> addManager(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long supermarketId,
                                                        @RequestBody AddManagerRequest request) throws IllegalAccessException {
        if (!hasRole(token, "admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        SuccessResponse response = new SuccessResponse();
        try {
            RegisterManagerRequest registerManagerRequest = RegisterManagerRequest.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .supermarketId(supermarketId)
                    .adminToken(token)
                    .build();

            supermarketService.addManager(supermarketId, registerManagerRequest);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-supermarket")
    public ResponseEntity<Supermarket> createSupermarket(@RequestHeader(value = "Authorization") String token,
                                                            @RequestBody CreateSupermarketRequest request) throws IllegalAccessException {
        if (!hasRole(token, "admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        return ResponseEntity.ok(supermarketService.createSupermarket(request.getName()));
    }

    @PutMapping("/edit-supermarket/{id}")
    public ResponseEntity<Supermarket> editSupermarket(@RequestHeader(value = "Authorization") String token,
                                                            @PathVariable("id") Long supermarketId,
                                                            @RequestBody EditSupermarketRequest request) throws IllegalAccessException {
        if (!hasRole(token, "admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        request.setAdminToken(token);
        return ResponseEntity.ok(supermarketService.editSupermarket(supermarketId, request));
    }

    @DeleteMapping("/delete-supermarket/{id}")
    public ResponseEntity<SuccessResponse> deleteSupermarket(@RequestHeader(value = "Authorization") String token,
                                                            @PathVariable("id") Long supermarketId) throws IllegalAccessException {
        if (!hasRole(token, "admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        return ResponseEntity.ok(SuccessResponse.builder().success(true).build());
    }

    @GetMapping("/all-supermarket")
    public ResponseEntity<List<Supermarket>> getAllSupermarkets() {
        return ResponseEntity.ok(supermarketService.getAllSupermarkets());
    }

    @GetMapping("/supermarket")
    public ResponseEntity<Supermarket> getSupermarket(@RequestParam("id") Long supermarketId) {
        return ResponseEntity.ok(supermarketService.getSupermarket(supermarketId));
    }

    public boolean hasRole(String token, String role) {
        if (token == null) return false;

        token = token.replace("Bearer ", "");
        return userService.getProfile(token).getRole().equalsIgnoreCase(role);
    }

    public boolean hasRoles(String token, List<String> roles) {
        if (token == null) return false;

        token = token.replace("Bearer ", "");

        String currentRole = userService.getProfile(token).getRole();
        for (String role : roles) {
            if (role.equalsIgnoreCase(currentRole)) {
                return true;
            }
        }
        return false;
    }
}
