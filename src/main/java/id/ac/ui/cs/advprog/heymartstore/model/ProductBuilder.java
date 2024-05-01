package id.ac.ui.cs.advprog.heymartstore.model;

public class ProductBuilder {
    private String name;
    private Long price;
    private Integer stock;

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

    public Product build() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        product.setStock(this.stock);
        return product;
    }

    private boolean stringValidation(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            System.out.println(c);
            if (!Character.isLetterOrDigit(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private void resetBuilder() {
        this.name = "";
        this.price = 0L;
        this.stock = 0;
    }
}
