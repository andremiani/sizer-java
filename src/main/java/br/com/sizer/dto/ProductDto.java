package br.com.sizer.dto;

import java.util.Map;

import br.com.sizer.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductDto {
    private Long id;
    private String storeId;
    private String productId;
    private String skuId;
    private String categoryId;
    private String name;
    private Map<String, Double> dimensions;
    private double distance;

    public ProductDto(Product product, double distance) {
        this.id = product.getId();
        this.storeId = product.getStore();
        this.productId = product.getProductId();
        this.skuId = product.getSkuId();
        this.categoryId = product.getCategory();
        this.name = product.getName();
        this.dimensions = product.getDimensions();
        this.distance = distance;
    }

    // Getters and setters
}
