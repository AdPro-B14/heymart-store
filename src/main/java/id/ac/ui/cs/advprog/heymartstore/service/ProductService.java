package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.dto.ModifyProductResponse;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;

import java.util.List;

public interface ProductService {
    public Product createProduct(Supermarket supermarket, String name, Long price, Integer stock);
    public Product editProduct(String UUID, Product changeAttribute);
    public Product deleteProduct(String UUID);
    public List<Product> getAllProduct();
    public List<Product> searchProductByName(String name);
    public Product searchProductById(String id);
}
