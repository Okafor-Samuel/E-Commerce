package com.samuel.ecommerce.product;

import com.samuel.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public Integer createProduct(ProductCreationRequest productCreationRequest) {
        var product = productMapper.toProduct(productCreationRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        //Extract the product id that wants to be purchased
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        //Check if they exist in the database
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        //Check if the sizes of the product to be purchased and available product are different
        if(productIds.size() != storedProducts.size()){
            throw  new ProductPurchaseException("One or more products does not exists");
        }

        //if the size are the same, i extract the Ids of the request
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        //created an object of the purchased product
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            //performed a check to make sure that the quantity is sufficient
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity fo product with id::" +productRequest.productId());
            }
            //update the available product
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id:: "+productId));
    }
    public List<ProductResponse> findAllProduct(){
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
