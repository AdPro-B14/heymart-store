package id.ac.ui.cs.advprog.heymartstore.service;
import id.ac.ui.cs.advprog.heymartstore.dto.DeleteProductRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.GetProfileResponse;
import id.ac.ui.cs.advprog.heymartstore.dto.ModifyProductResponse;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

//    private String getRole() {
//        GetProfileResponse response = webClient.get()
//                .uri(AUTH_BASE_URL + "/api/user/profile",
//                        uriBuilder -> uriBuilder.queryParam("email", managerId).build())
//                .retrieve()
//                .bodyToMono(GetProfileResponse.class)
//                .block();
//        System.out.println(response.role);
//        return response.role;
//    }

//    public boolean isManager() {
//        String role = getRole();
//        return role.equals("MANAGER");
//    }

    public Product createProduct(Product product)  {
        if (true) {
            productRepository.save(product);
            return product;
        }
        return null;
    }

    public Product editProduct(String UUID, Product product) {
        if (true) {
            System.out.println(UUID);
            Product productModify = productRepository.findById(UUID).orElseThrow();
            productModify.setName(product.getName());
            productModify.setPrice(product.getPrice());
            productModify.setStock(product.getStock());
            productRepository.save(productModify);
            return productModify;
        }
        return null;
    }

    public Product deleteProduct(String UUID) {
        if (true) {
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

    public List<Product> searchProductByName(String name) {
        System.out.println(name);
        List<Product> searchProductByName = productRepository.findAllByNameIsContainingIgnoreCase(name);
        return searchProductByName;
    }

    public Product searchProductById(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}
