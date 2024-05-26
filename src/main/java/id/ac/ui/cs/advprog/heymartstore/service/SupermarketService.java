package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.EditSupermarketRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.RegisterManagerRequest;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;

import java.util.List;

public interface SupermarketService {
    Supermarket addManager(Long supermarketId, RegisterManagerRequest request);

    Supermarket removeManager(Long supermarketId, String token, String managerEmail);

    Supermarket getSupermarket(Long id);

    List<Supermarket> getAllSupermarkets();

    Supermarket createSupermarket(String adminToken, String name);

    void deleteSupermarket(Long id);

    Supermarket editSupermarket(Long id, EditSupermarketRequest newSupermarket);
}
