package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.DeleteProductRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.GetProfileResponse;
import id.ac.ui.cs.advprog.heymartstore.dto.ModifyProductResponse;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    private static String AUTH_BASE_URL = "";
    private WebClient webClient;

    public Product createProduct(Supermarket supermarket, String name, Long price, Integer stock)  {
        Product product = new ProductBuilder()
                .setName(name)
                .setStock(stock)
                .setPrice(price)
                .setSupermarket(supermarket)
                .build();

        return productRepository.save(product);
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

    public List<Product> getAllProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }

    public List<Product> searchProductByName(String name) {
        System.out.println(name);
        List<Product> searchProductByName = productRepository.findAllByNameIsContainingIgnoreCase(name);
        return searchProductByName;
    }

    public Product searchProductById(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}
