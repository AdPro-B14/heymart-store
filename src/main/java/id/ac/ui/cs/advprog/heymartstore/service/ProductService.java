package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;

import java.util.List;

public interface ProductService {
    public Supermarket createProduct(Product product);
    public Product editProduct(String UUID, Product changeAttribute);
    public Product deleteProduct(String UUID);
    public List<Product> getAllProduct(Long supermarket);
    public List<Product> searchProductByName(String name);
    public Product searchProductById(String id);
}
