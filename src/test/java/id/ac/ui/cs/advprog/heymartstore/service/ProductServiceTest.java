package id.ac.ui.cs.advprog.heymartstore.service;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl service;

    private static ArrayList<Product> productList;

    @BeforeEach
    void setUp(){
        productList = new ArrayList<>();
    }

    @AfterEach
    void deleteList()
    {
        productList = new ArrayList<>();
    }

    @Test
    public void testCreateProduct() {
        Product product = new ProductBuilder()
                .setName("Indomie")
                .setPrice(10000L)
                .setStock(10)
                .build();
        productList.add(product);
        assertEquals(productList.getFirst().getName(),"Indomie");
        assertEquals(productList.getFirst().getPrice(), 10000L);
        assertEquals(productList.getFirst().getStock(), 10);
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

        assertEquals("123", productList.getFirst().getId());
    }
}