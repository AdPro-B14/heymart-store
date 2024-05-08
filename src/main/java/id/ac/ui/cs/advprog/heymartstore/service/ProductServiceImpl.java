package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupermarketRepository supermarketRepository;
    private static String AUTH_BASE_URL = "";
    private WebClient webClient;

    public Supermarket createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        }
        productRepository.save(product);
        return supermarketRepository.save(product.getSupermarket());
    }

    public Product editProduct(String UUID, Product changeAttribute) {
        Product product = productRepository.findById(UUID).orElseThrow();
        product.setName(changeAttribute.getName());
        product.setPrice(changeAttribute.getPrice());
        product.setStock(changeAttribute.getStock());
        return productRepository.save(product);
    }

    public Product deleteProduct(String UUID) {
        Product product = productRepository.findById(UUID).orElseThrow();
        productRepository.delete(product);
        return product;
    }

    public List<Product> getAllProduct(Long supermarket) {
        List<Product> allProduct = supermarketRepository.getReferenceById(supermarket).getProducts();
        return allProduct;
    }

    public List<Product> searchProductByName(String name) {
        List<Product> searchProductByName = productRepository.findAllByNameIsContainingIgnoreCase(name);
        return searchProductByName;
    }

    public Product searchProductById(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}
