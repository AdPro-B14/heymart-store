package id.ac.ui.cs.advprog.heymartstore.dto;

import id.ac.ui.cs.advprog.heymartstore.model.Product;
import lombok.Builder;

@Builder
public class AddProductResponse {
    public Long supermarketId;
    public String productId;
}
