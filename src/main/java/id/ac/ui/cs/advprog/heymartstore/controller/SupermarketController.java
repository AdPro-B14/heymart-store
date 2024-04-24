package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.dto.*;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.service.SupermarketService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/supermarket")
@RequiredArgsConstructor
public class SupermarketController {
    private final SupermarketService supermarketService;
    private final WebClient webClient;

    @GetMapping("/profile")
    public ResponseEntity<GetSupermarketProfileResponse> getProfile(@RequestParam Long id) {
        Supermarket supermarket = supermarketService.getSupermarket(id);

        GetSupermarketProfileResponse response = new GetSupermarketProfileResponse();
        response.id = supermarket.getId();
        response.name = supermarket.getName();
        response.managers = new ArrayList<>();

        for (String managerId : supermarket.getManagers()) {
            System.out.println(managerId);
            GetProfileResponse profileResponse = webClient.get()
                    .uri("http://localhost:3031/api/user/profile",
                            uriBuilder -> uriBuilder.queryParam("email", managerId).build())
                    .retrieve()
                    .bodyToMono(GetProfileResponse.class)
                    .block();

            response.managers.add(profileResponse.name);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-manager")
    public ResponseEntity<AddManagerResponse> addManager(@RequestBody AddManagerRequest request) {
        AddManagerResponse response = new AddManagerResponse();
        System.out.println(request.supermarketId);
        try {
            supermarketService.addManager(request.supermarketId, request.managerEmail);
            response.success = true;
        } catch (Exception e) {
            System.out.println(e);
            response.success = false;
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-supermarket")
    public ResponseEntity<Supermarket> createSupermarket(CreateSupermarketRequest request) {
        return ResponseEntity.ok(supermarketService.createSupermarket(request.name));
    }
}
