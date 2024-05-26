package id.ac.ui.cs.advprog.heymartstore.controller;

import id.ac.ui.cs.advprog.heymartstore.controller.ProductController;

import id.ac.ui.cs.advprog.heymartstore.dto.CreateProductRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.DeleteProductRequest;
import id.ac.ui.cs.advprog.heymartstore.dto.ModifyProductResponse;
import id.ac.ui.cs.advprog.heymartstore.model.Product;
import id.ac.ui.cs.advprog.heymartstore.model.ProductBuilder;
import id.ac.ui.cs.advprog.heymartstore.model.Supermarket;
import id.ac.ui.cs.advprog.heymartstore.service.JwtService;
import id.ac.ui.cs.advprog.heymartstore.service.ProductServiceImpl;
import id.ac.ui.cs.advprog.heymartstore.service.SupermarketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @InjectMocks
    ProductController controller;

    @MockBean
    private SupermarketService supermarketService;

    @MockBean
    private JwtService jwtService;

    private static ArrayList<Product> productList;
    private static Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("123");
        product.setName("Test Product");
        product.setPrice(100L);
        product.setStock(10);
        Supermarket supermarket = new Supermarket();
        supermarket.setId(1L);
        supermarket.setProducts(productList);
        supermarket.setName("Supermarket");
        product.setSupermarket(supermarket);
    }

    @Test
    void testAllProduct() throws Exception {
        when(productService.getAllProduct(1L)).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/product/all-product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Test Product")));
    }

    @Test
    void testQueryProduct() throws Exception {
        Supermarket supermarket =  new Supermarket();
        supermarket.setId(1L);
        supermarket.setName("Test Supermarket");

        when(supermarketService.getSupermarket(1L)).thenReturn(supermarket);
        when(productService.searchProductByName(supermarket, "Test")).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/product/search/1/Test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Test Product")));
    }

    @Test
    void testCreateProduct() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.supermarketId = 1L;
        request.stock = 50;
        request.price = 200L;
        request.name = "New Product";

        when(jwtService.extractRole("valid-token")).thenReturn("manager");
        Supermarket supermarket =  new Supermarket();
        supermarket.setId(1L);
        supermarket.setName("Test Supermarket");
        when(supermarketService.getSupermarket(1L)).thenReturn(supermarket);
        when(productService.createProduct(product)).thenReturn(supermarket);

        mockMvc.perform(post("/product/create")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"supermarketId\":1,\"name\":\"New Product\",\"stock\":50,\"price\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Product")));
    }
    @Test
    void testErrorCreateProduct() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.supermarketId = 1L;
        request.stock = 50;
        request.price = 200L;
        request.name = "New Product";

        when(jwtService.extractRole("valid-token")).thenReturn("customer");
        Supermarket supermarket =  new Supermarket();
        supermarket.setId(1L);
        supermarket.setName("Test Supermarket");
        when(supermarketService.getSupermarket(1L)).thenReturn(supermarket);
        when(productService.createProduct(product)).thenReturn(supermarket);

        mockMvc.perform(post("/product/create")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"supermarketId\":1,\"name\":\"New Product\",\"stock\":50,\"price\":200.0}"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testDeleteProduct() throws Exception {
        DeleteProductRequest request = new DeleteProductRequest();
        request.UUID = "1";

        when(jwtService.extractRole("valid-token")).thenReturn("manager");
        when(productService.deleteProduct("1")).thenReturn(product);

        mockMvc.perform(post("/product/delete")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"UUID\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Product")));
    }

    @Test
    void testSearchProductById() throws Exception {
        when(productService.searchProductById("1")).thenReturn(product);

        mockMvc.perform(get("/product/findById/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test Product")));
    }

    @Test
    void testSearchProductByMultipleId() throws Exception {
        when(productService.searchProductById("1")).thenReturn(product);

        mockMvc.perform(get("/product/findByMultipleId/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Test Product")));
    }

    @Test
    void testEditProduct() throws Exception {
        ModifyProductResponse request = new ModifyProductResponse();
        request.id = "123";
        request.name = "Updated Product";
        request.price = 150L;
        request.stock = 20;

        when(jwtService.extractRole("valid-token")).thenReturn("manager");

        Product existingProduct = new Product();
        existingProduct.setId("123");
        existingProduct.setName("Test Product");
        existingProduct.setPrice(100L);
        existingProduct.setStock(10);

        Product updatedProduct = new ProductBuilder()
                .setName("Updated Product")
                .setPrice(150L)
                .setStock(20)
                .build();
        updatedProduct.setId("123");

        when(productService.editProduct("123", updatedProduct)).thenReturn(updatedProduct);

        mockMvc.perform(post("/product/edit")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"123\",\"name\":\"Updated Product\",\"price\":150.0,\"stock\":20}"))
                .andExpect(status().isOk());
    }

}
