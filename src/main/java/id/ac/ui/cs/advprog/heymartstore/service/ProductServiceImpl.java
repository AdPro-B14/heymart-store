package id.ac.ui.cs.advprog.heymartstore.service;
import id.ac.ui.cs.advprog.heymartstore.dto.GetProfileResponse;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    private WebClient webClient;

    private String getRole() {
        GetProfileResponse response = webClient.get()
                .uri("http://localhost:3032/profile/")
                .retrieve()
                .bodyToMono(GetProfileResponse.class)
                .block();
        return response.role;
    }

    public boolean isManager() {
        String role = getRole();
        return role.equals("MANAGER");
    }

    public Product createProduct(String name, Long price, Integer stock)  {
        if (isManager()) {
            Product product = new ProductBuilder()
                    .setName(name)
                    .setStock(stock)
                    .setPrice(price)
                    .build();
            productRepository.save(product);
            return product;
        }
        return null;
    }

    public Product editProduct(String UUID, Product changeAttribute) {
        if (isManager()) {
            Product product = productRepository.findById(UUID).orElseThrow();
            product.setName(changeAttribute.getName());
            product.setPrice(changeAttribute.getPrice());
            product.setStock(changeAttribute.getStock());
            return product;
        }
        return null;
    }

    public Product deleteProduct(String UUID) {
        if (isManager()) {
            Product product = productRepository.findById(UUID).orElseThrow();
            productRepository.delete(product);
            return product;
        }
        return null;
    }

    public List<Product> getAllProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }

    public List<Product> searchProduct(String name) {
        List<Product> searchProductByName = productRepository.searchProduct(name);
        return searchProductByName;
    }
}
