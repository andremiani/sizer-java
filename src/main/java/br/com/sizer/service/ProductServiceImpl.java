package br.com.sizer.service;

import br.com.sizer.exception.ResourceNotFoundException;
import br.com.sizer.model.Product;
import br.com.sizer.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return product;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> resultPage = productRepository.findAll(pageable);
        if (pageable.getPageNumber() > resultPage.getTotalPages())
            throw new ResourceNotFoundException("Product", "id", resultPage);
        return resultPage;
    }

    @Override
    public List<Product> findAll(Specification<Product> spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product update(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (product == null)
            throw new ResourceNotFoundException("Product", "id", id);

        product.setName(productDetails.getName());
        product.setProductId(productDetails.getProductId());
        product.setSkuId(productDetails.getSkuId());
        product.setDimensions(productDetails.getDimensions());
        product.setCategory(productDetails.getCategory());
        product.setStore(productDetails.getStore());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (product == null)
            throw new ResourceNotFoundException("Product", "id", id);

        productRepository.delete(product);
    }

    public Page<Product> recommendProducts(Map<String, Double> userMeasurements, String category_id,
            Pageable pageable) {
        List<Product> allProducts = (category_id == null || category_id.isEmpty())
                ? productRepository.findAll()
                : productRepository.findByCategory(category_id);

        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> {
                    double sumOfSquares = 0.0;
                    for (Map.Entry<String, Double> entry : product.getDimensions().entrySet()) {
                        String key = entry.getKey();
                        double productValue = entry.getValue();
                        double userValue = userMeasurements.getOrDefault(key, productValue);
                        sumOfSquares += Math.pow(productValue - userValue, 2);
                    }
                    double euclideanDistance = Math.sqrt(sumOfSquares);
                    double tolerance = 0.1 * sumOfSquares;
                    return euclideanDistance <= tolerance;
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        return new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());
    }

}
