package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import id.ac.ui.cs.advprog.heymartstore.repository.SupermarketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    SupermarketRepository supermarketRepository;

    @InjectMocks
    ProductServiceImpl service;

    private static List<Product> productList;
    private static ArrayList<Supermarket> supermarketList;

    @BeforeEach
    void setUp(){
        productList = new ArrayList<>();
        supermarketList = new ArrayList<>();
    }

    @AfterEach
    void deleteList()
    {
        productList = new ArrayList<>();
    }


    private Product createProduct(Product product) {
        productList.add(product);
        return product;
    }

    @Test
    public void testCreateProduct() {
        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        when(productRepository.save(product)).thenReturn(createProduct(product));
        service.createProduct(product);
        assertEquals(productList.getFirst().getName(),"Indomie");
        assertEquals(productList.getFirst().getPrice(), 10000L);
        assertEquals(productList.getFirst().getStock(), 10);
    }

    @Test
    public void testErrorCreateProduct() {
        assertThrows(IllegalArgumentException.class, ()->service.createProduct(null));
    }


    private Product editProduct(Product product, String name) {
        product.setName(name);
        productList.add(product);
        return product;
    }

    @Test
    public void testEditProduct() {
        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        product.setId("123");

        assertEquals(product.getName(),"Indomie");

        when(productRepository.save(product)).thenReturn(editProduct(product, "Ayam"));
        when(productRepository.findById("123")).thenReturn(Optional.of(product));
        service.editProduct("123", product);

        assertEquals(productList.getFirst().getName(),"Ayam");
        assertEquals(productList.getFirst().getPrice(), 10000L);
        assertEquals(productList.getFirst().getStock(), 10);
    }

    private boolean deleteProduct(Product product) {
        productList.remove(product);
        return true;
    }

    @Test
    public void testDeleteProduct() {
        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        product.setId("123");

        when(productRepository.findById("123")).thenReturn(Optional.of(product));
        service.deleteProduct("123");

        verify(productRepository, times(1)).delete(product);

        deleteProduct(product);

        assertEquals(0, productList.size());
    }

    @Test
    public void testGetAllProduct() {
        Product product1 = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        productList.add(product1);
        Supermarket supermarket = new Supermarket();
        supermarket.setProducts(productList);
        when(supermarketRepository.getReferenceById(1L)).thenReturn(supermarket);
        List<Product> product = service.getAllProduct(1L);
        assertEquals("Indomie", product.getFirst().getName());
        assertEquals(10000L, product.getFirst().getPrice());
        assertEquals(10, product.getFirst().getStock());
    }

    @Test
    public void testFindProductById() {
        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        product.setId("123");
        productList.add(product);

        when(productRepository.findById("123")).thenReturn(Optional.of(product));

        Product queryProduct = service.searchProductById("123");
        assertEquals("123", queryProduct.getId());
    }

    @Test
    public void testSearchProductByName() {
        Supermarket supermarket1 = Supermarket.builder()
                .id(1L)
                .name("Alfamart Kutek")
                .managers(new ArrayList<>())
                .products(new ArrayList<>()).build();
        supermarket1.getManagers().add("williams@gmail.com");

        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .setSupermarket(supermarket1)
                .build();

        supermarket1.getProducts().add(product);
        productList.add(product);
        supermarketList.add(supermarket1);

        when(productRepository.findBySupermarketAndNameContainingIgnoreCase(supermarketList.getFirst(), "Ind")).
                thenReturn(supermarketList.getFirst().getProducts());

        assertEquals(supermarketList.getFirst(), product.getSupermarket());
        List<Product> queryProduct = service.searchProductByName(supermarket1, "Ind");
        assertTrue(queryProduct.getFirst().getName().contains(product.getName()));
    }
}