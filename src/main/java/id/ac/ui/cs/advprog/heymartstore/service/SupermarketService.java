package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.EditSupermarketRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.RemoveManagerRequest;
import id.ac.ui.cs.advprog.heymartstore.exception.ManagerAlreadyAddedException;
import id.ac.ui.cs.advprog.heymartstore.exception.ManagerRegistrationFailedException;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import id.ac.ui.cs.advprog.heymartstore.rest.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupermarketService {
    private final SupermarketRepository supermarketRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;

    public Supermarket addManager(Long supermarketId, RegisterManagerRequest request) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();

        if (supermarket.getManagers().contains(request.email)) {
            throw new ManagerAlreadyAddedException(request.email);
        }

        if (authService.registerManager(request)) {
            supermarket.getManagers().add(request.email);
            supermarketRepository.save(supermarket);
        } else {
            throw new ManagerRegistrationFailedException(request.email);
        }

        return supermarket;
    }

    public Supermarket removeManager(Long supermarketId, String token, String managerEmail) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow();

        if (!supermarket.getManagers().contains(managerEmail)) {
            throw new IllegalArgumentException();
        }

        authService.removeManager(RemoveManagerRequest.builder()
                .email(managerEmail)
                .supermarketId(supermarketId)
                .adminToken(token)
                .build());

        supermarket.getManagers().remove(managerEmail);
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

    public void deleteSupermarket(Long id) {
        Supermarket supermarket = getSupermarket(id);

        supermarketRepository.delete(supermarket);
    }

    public Supermarket editSupermarket(Long id, EditSupermarketRequest newSupermarket) {
        if (newSupermarket == null) {
            throw new IllegalArgumentException();
        }

        Supermarket supermarket = getSupermarket(id);

        if (newSupermarket.getName() != null) {
            supermarket.setName(newSupermarket.getName());
        }

        supermarketRepository.save(supermarket);

        if (newSupermarket.getManagers() != null) {
            List<String> removedManagers = new ArrayList<>();
            for (String currentManager : supermarket.getManagers()) {
                if (!newSupermarket.getManagers().contains(currentManager)) {
                    removedManagers.add(currentManager);
                }
            }

            for (String newManager : newSupermarket.getManagers()) {
                if (!supermarket.getManagers().contains(newManager)) {
                    throw new IllegalArgumentException("You can't add new manager through this endpoint.");
                }
            }
            for (String removedManager : removedManagers) {
                removeManager(supermarket.getId(), newSupermarket.getAdminToken(), removedManager);
            }
        }

        return supermarketRepository.findById(id).orElseThrow();
    }
}
