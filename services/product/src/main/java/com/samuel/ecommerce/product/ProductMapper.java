package com.samuel.ecommerce.product;

import com.samuel.ecommerce.category.Category;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper {
    public Product toProduct(ProductCreationRequest productCreationRequest) {
        return Product.builder()
                .id(productCreationRequest.id())
                .name(productCreationRequest.name())
                .description(productCreationRequest.description())
                .price(productCreationRequest.price())
                .availableQuantity(productCreationRequest.availableQuantity())
                .category(
                        Category.builder()
                        .id(productCreationRequest.id())
                        .build())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
