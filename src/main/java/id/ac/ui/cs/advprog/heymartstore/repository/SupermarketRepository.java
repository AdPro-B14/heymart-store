package id.ac.ui.cs.advprog.heymartstore.repository;

import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;

import java.util.Optional;

public interface SupermarketRepository {
    Optional<Supermarket> findById(Long Id);
}
