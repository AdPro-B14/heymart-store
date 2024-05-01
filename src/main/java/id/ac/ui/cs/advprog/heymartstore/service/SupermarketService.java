package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SupermarketService {
    private final SupermarketRepository supermarketRepository;

    public Supermarket addManager(Long supermarketId, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();
        supermarket.getManagers().add(managerEmail);
        supermarketRepository.save(supermarket);
        return supermarket;
    }

    public Supermarket addProduct(Long supermarketId, Product product) throws IllegalArgumentException {
        return null;
    }

    public Supermarket getSupermarket(Long id) {
        return supermarketRepository.findById(id).orElseThrow();
    }

    public Supermarket createSupermarket(String name) {
        Supermarket supermarket = new Supermarket();
        supermarket.setName(name);
        supermarket.setManagers(new ArrayList<String>());
        return supermarketRepository.save(supermarket);
    }
}
