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

@RestController
@RequestMapping("/supermarket")
@RequiredArgsConstructor
public class SupermarketController {
    @Value("${spring.route.gateway_url}")
    private String GATEWAY_URL;

    private final SupermarketService supermarketService;
    private final JwtService jwtService;

    private final WebClient webClient;

    @GetMapping("/profile")
    public ResponseEntity<GetSupermarketProfileResponse> getProfile(@RequestParam Long id) {
        Supermarket supermarket = supermarketService.getSupermarket(id);

        GetSupermarketProfileResponse response = new GetSupermarketProfileResponse();
        response.id = supermarket.getId();
        response.name = supermarket.getName();
        response.managers = new ArrayList<>();
        for (String managerId : supermarket.getManagers()) {
            GetProfileResponse profileResponse = webClient.get()
                    .uri(GATEWAY_URL + "/api/user/profile",
                            uriBuilder -> uriBuilder.queryParam("email", managerId).build())
                    .retrieve()
                    .bodyToMono(GetProfileResponse.class)
                    .block();

            response.managers.add(profileResponse.name);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-manager")
    public ResponseEntity<SuccessResponse> addManager(@RequestHeader(value = "Authorization") String id,
                                                      @RequestBody AddManagerRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        SuccessResponse response = new SuccessResponse();
        try {
            supermarketService.addManager(request.supermarketId, request.managerEmail);
            response.success = true;
        } catch (Exception e) {
            System.out.println(e);
            response.success = false;
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove-manager")
    public ResponseEntity<SuccessResponse> removeManager(@RequestHeader(value = "Authorization") String id,
                                                         @RequestBody AddManagerRequest request) throws IllegalAccessException {
        String token = id.replace("Bearer ", "");
        if (!jwtService.extractRole(token).equalsIgnoreCase("admin")) {
            throw new IllegalAccessException("You have no access.");
        }

        SuccessResponse response = new SuccessResponse();

        try {
            supermarketService.removeManager(request.supermarketId, request.managerEmail);
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
                || !jwtService.extractRole(token).equalsIgnoreCase("admin")) {
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
}
