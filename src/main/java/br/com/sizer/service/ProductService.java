package br.com.sizer.service;

import br.com.sizer.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface ProductService {

    public List<Product> findByCategory(String category);

    public Product findOne(Long id);

    public List<Product> findAll(Specification<Product> spec);

    public Page<Product> findAll(Pageable pageable);

    public Page<Product> recommendProducts(Map<String, Double> userMeasurements, String category_id, Pageable pageable);

    public Product create(Product company);

    public Product update(Long id, Product companyDetails);

    public void delete(Long id);
}
