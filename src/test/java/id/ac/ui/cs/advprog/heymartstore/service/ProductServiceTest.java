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

@SpringBootTest
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
    public void testCreateProduct(String name, Long price, Integer stock) {
        Product product = new ProductBuilder()
                .setName(name)
                .setPrice(price)
                .setStock(stock)
                .build();
        productList.add(product);
        assertEquals(productList.getFirst().getName(), name);
        assertEquals(productList.getFirst().getPrice(), price);
        assertEquals(productList.getFirst().getStock(), stock);

    }
}