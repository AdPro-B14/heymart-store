package id.ac.ui.cs.advprog.heymartstore.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Supermarket {
    private Integer id;

    private String name;

    private List<Product> products;

    public void addProduct(Product product) {
    }

    public void removeProduct(Product newProduct) {
    }
}
