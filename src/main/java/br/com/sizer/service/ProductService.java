package br.com.sizer.service;

import br.com.sizer.dto.ProductDto;
import br.com.sizer.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface ProductService {

    public List<Product> findByCategory(List<String> categories, Pageable pageable);

    public Product findOne(Long id);

    public List<Product> findAll(Specification<Product> spec);

    public Page<Product> findAll(Pageable pageable);

    public Page<ProductDto> getRecommendedProducts(Map<String, Double> userMeasurements, Double tolerance,
            List<String> categories,
            Pageable pageable);

    public Product create(String storeId, String productId, String skuId, String categoryId, String name,
            Map<String, Double> dimensions);

    public Product update(Long id, Product companyDetails);

    public void delete(Long id);
}
