package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-product")
    public ResponseEntity<List<Product>> allProduct() {
        List<Product> allProduct = productService.getAllProduct();
        return ResponseEntity.ok(allProduct);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Product>> queryProduct(String query) {
        List<Product> queryProduct = productService.searchProduct(query);
        return ResponseEntity.ok(queryProduct);
    }
}
