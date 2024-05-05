package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.EditSupermarketRequest;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupermarketService {
    private final SupermarketRepository supermarketRepository;
    private final ProductRepository productRepository;

    public Supermarket addManager(Long supermarketId, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();
        supermarket.getManagers().add(managerEmail);
        supermarketRepository.save(supermarket);
        return supermarket;
    }

    public Supermarket removeManager(Long supermarketId, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();

        if (!supermarket.getManagers().contains(managerEmail)) {
            throw new IllegalArgumentException();
        }

        supermarket.getManagers().remove(managerEmail);
        supermarketRepository.save(supermarket);
        return supermarket;
    }

    public Supermarket addProduct(Long supermarketId, Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = getSupermarket(supermarketId);

        product.setSupermarket(supermarket);
        supermarket.getProducts().add(product);

        productRepository.save(product);
        return supermarketRepository.save(supermarket);
    }

    public Supermarket getSupermarket(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return supermarketRepository.findById(id).orElseThrow();
    }

    public List<Supermarket> getAllSupermarkets() {
        return supermarketRepository.findAll();
    }

    public Supermarket createSupermarket(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = new Supermarket();
        supermarket.setName(name);
        supermarket.setManagers(new ArrayList<>());
        supermarket.setProducts(new ArrayList<>());

        return supermarketRepository.save(supermarket);
    }

    public Supermarket deleteSupermarket(Long id) {
        Supermarket supermarket = getSupermarket(id);

        supermarketRepository.delete(supermarket);

        return supermarket;
    }

    public Supermarket editSupermarket(Long id, EditSupermarketRequest newSupermarket) {
        if (newSupermarket == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = getSupermarket(id);

        supermarket.setName(newSupermarket.getName());

        return supermarketRepository.save(supermarket);
    }
}
