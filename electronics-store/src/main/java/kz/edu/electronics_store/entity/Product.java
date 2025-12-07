package kz.edu.electronics_store.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Товар интернет-магазина электроники.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String sku;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductType type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category category;

    protected Product() {}

    public Product(String sku, String name, BigDecimal price, int stock, ProductType type, Category category) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
    public ProductType getType() { return type; }
    public Category getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setType(ProductType type) { this.type = type; }
    public void setCategory(Category category) { this.category = category; }
}
