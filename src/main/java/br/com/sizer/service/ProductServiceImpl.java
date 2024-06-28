package br.com.sizer.service;

import br.com.sizer.dto.ProductDto;
import br.com.sizer.exception.ResourceNotFoundException;
import br.com.sizer.model.Product;
import br.com.sizer.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product create(String storeId, String productId, String skuId, String categoryId, String name,
            Map<String, Double> productDimensions) {
        Product product = new Product();
        product.setStore(storeId);
        product.setProductId(productId);
        product.setSkuId(skuId);
        product.setCategory(categoryId);
        product.setName(name);
        product.setDimensions(productDimensions);
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
    public List<Product> findByCategory(List<String> categories, Pageable pageable) {
        return productRepository.findAllByCategoryIn(categories, pageable).getContent();
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

    @Transactional
    public Page<ProductDto> getRecommendedProducts(Map<String, Double> bodyMeasurements, Double tolerance,
            List<String> categories,
            Pageable pageable) {
        List<Product> allProducts = productRepository.findAll(pageable).getContent();

        // Initialize productDimensions
        allProducts.forEach(product -> product.getDimensions().size());

        // Filter products based on Euclidean distance and body measurements
        List<ProductDto> filteredProducts = allProducts.stream()
                .filter(product -> {
                    Map<String, Double> productDimensions = product.getDimensions();

                    // Verifica se as dimensões do produto são válidas em relação às medidas do
                    // usuário
                    return areDimensionsValid(productDimensions, bodyMeasurements, tolerance);
                })
                .map(product -> {
                    // Normalizar as medidas do usuário em relação às dimensões do produto
                    // Map<String, Double> normalizedMeasurements =
                    // normalizeMeasurements(product.getDimensions(),
                    // bodyMeasurements);

                    double distance = calculateEuclideanDistance(product.getDimensions(), bodyMeasurements);
                    return new ProductDto(product, distance);
                })
                .collect(Collectors.toList());

        // Create a new Page object with filtered products
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        return new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());
    }

    private double calculateEuclideanDistance(Map<String, Double> productDimensions, Map<String, Double> measurements) {
        // Verifica se ambos os mapas têm as mesmas chaves para comparação precisa
        Set<String> commonKeys = new HashSet<>(productDimensions.keySet());
        commonKeys.retainAll(measurements.keySet());

        // Calcula a distância euclidiana apenas para as chaves comuns
        double sumOfSquares = commonKeys.stream()
                .mapToDouble(key -> {
                    double dimensionValue = productDimensions.getOrDefault(key, 0.0);
                    double measurementValue = measurements.getOrDefault(key, 0.0);
                    return Math.pow(dimensionValue - measurementValue, 2);
                })
                .sum();

        return Math.sqrt(sumOfSquares);
    }

    // private double calculateTolerance(Map<String, Double> productDimensions,
    // Double tolerance) {
    // double sumOfSquares = productDimensions.values().stream().mapToDouble(value
    // -> Math.pow(value, 2)).sum();
    // return Math.sqrt(sumOfSquares) * tolerance;
    // }

    // // Método para normalizar as medidas do usuário em relação às dimensões do
    // // produto
    // private static Map<String, Double> normalizeMeasurements(Map<String, Double>
    // productDimensions,
    // Map<String, Double> measurements) {
    // Map<String, Double> normalizedMeasurements = new HashMap<>();

    // // Iterar sobre as dimensões do produto
    // for (Map.Entry<String, Double> entry : productDimensions.entrySet()) {
    // String dimension = entry.getKey();
    // double dimensionValue = entry.getValue();

    // // Verificar se a medida do usuário está presente e normalizá-la
    // if (measurements.containsKey(dimension)) {
    // double measurementValue = measurements.get(dimension);

    // // Normalização simples: ajustar conforme necessário
    // double normalizedValue = (measurementValue - dimensionValue) /
    // dimensionValue;
    // System.out.println("normalizedValue" + normalizedValue);

    // // Adicionar ao mapa de medidas normalizadas
    // normalizedMeasurements.put(dimension, normalizedValue);
    // }
    // }

    // return normalizedMeasurements;
    // }

    private boolean areDimensionsValid(Map<String, Double> productDimensions, Map<String, Double> userMeasurements,
            double tolerance) {
        for (String key : userMeasurements.keySet()) {
            double userMeasurement = userMeasurements.get(key);
            double productDimension = productDimensions.getOrDefault(key, 0.0);
            // Verifica se a dimensão do produto é maior ou igual à medida do usuário menos
            // a tolerância
            if (productDimension < (userMeasurement - (userMeasurement * tolerance))) {
                return false;
            }
        }
        return true;
    }
}
