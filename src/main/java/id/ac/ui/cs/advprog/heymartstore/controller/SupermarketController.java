package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.dto.*;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.service.JwtService;
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
    private final JwtService jwtService;

    @PutMapping("/add-manager/{id}")
    public ResponseEntity<SuccessResponse> addManager(@RequestHeader(value = "Authorization") String id, @PathVariable("id") Long supermarketId,
                                                      @RequestBody AddManagerRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        SuccessResponse response = new SuccessResponse();
        try {
            RegisterManagerRequest registerManagerRequest = RegisterManagerRequest.builder()
                    .name(request.name)
                    .email(request.email)
                    .password(request.password)
                    .managerSupermarketId(supermarketId)
                    .role("MANAGER")
                    .adminToken(token)
                    .build();

            supermarketService.addManager(supermarketId, registerManagerRequest);
            response.success = true;
        } catch (Exception e) {
            System.out.println(e);
            response.success = false;
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/remove-manager/{id}")
    public ResponseEntity<SuccessResponse> removeManager(@RequestHeader(value = "Authorization") String id,
                                                         @PathVariable("id") Long supermarketId,
                                                         @RequestBody AddManagerRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        SuccessResponse response = new SuccessResponse();

        try {
            supermarketService.removeManager(supermarketId, request.email);
            response.success = true;
        } catch (Exception e) {
            response.success = false;
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-product")
    public ResponseEntity<AddProductResponse> addProduct(@RequestHeader(value = "Authorization") String id,
                                                         @RequestBody AddProductRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("manager")
                && !jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        Product product = Product.getBuilder()
                .setName(request.getName())
                .setPrice(request.getPrice())
                .setStock(request.getStock())
                .build();

        Supermarket supermarket = supermarketService.addProduct(request.supermarketId, product);

        return ResponseEntity.ok(AddProductResponse.builder().supermarketId(supermarket.getId()).productId(product.getId()).build());
    }

    @PostMapping("/create-supermarket")
    public ResponseEntity<Supermarket> createSupermarket(@RequestHeader(value = "Authorization") String id,
                                                         @RequestBody CreateSupermarketRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        return ResponseEntity.ok(supermarketService.createSupermarket(request.name));
    }

    @PutMapping("/edit-supermarket/{id}")
    public ResponseEntity<Supermarket> editSupermarket(@RequestHeader(value = "Authorization") String id,
                                                         @PathVariable("id") Long supermarketId,
                                                         @RequestBody EditSupermarketRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        return ResponseEntity.ok(supermarketService.editSupermarket(supermarketId, request));
    }

    @DeleteMapping("/delete-supermarket/{id}")
    public ResponseEntity<SuccessResponse> deleteSupermarket(@RequestHeader(value = "Authorization") String id,
                                                         @PathVariable("id") Long supermarketId) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
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
}
