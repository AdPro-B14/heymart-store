package id.ac.ui.cs.advprog.heymartstore.model;

public class ProductBuilder {
    private String name;
    private Long price;
    private Integer stock;
    private Supermarket supermarket;

    public ProductBuilder setName(String name) {
        if ((name == null) || name.equals("")) {
            throw new IllegalArgumentException();
        }

        if (!stringValidation(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(Long price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
        return this;
    }

    public ProductBuilder setStock(Integer stock) {
        if (stock < 0) {
            throw new IllegalArgumentException();
        }

        this.stock = stock;
        return this;
    }

    public ProductBuilder setSupermarket(Supermarket supermarket) {
        if (supermarket == null) {
            throw new IllegalArgumentException();
        }

        this.supermarket = supermarket;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setStock(this.stock);
        product.setSupermarket(this.supermarket);
        return product;
    }

    private boolean stringValidation(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }
}
