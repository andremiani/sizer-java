package br.com.sizer.controller;

import br.com.sizer.utill.ProductSpecificationsBuilder;
import br.com.sizer.dto.ProductDto;
import br.com.sizer.model.Product;
import br.com.sizer.payload.requests.RecommendationRequest;
import br.com.sizer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    // -------------------create product -------------------
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> requestBody) {
        try {
            String storeId = (String) requestBody.get("store_id");
            String productId = (String) requestBody.get("product_id");
            String skuId = (String) requestBody.get("sku_id");
            String categoryId = (String) requestBody.get("category_id");
            String name = (String) requestBody.get("name");

            Object dimensionsObj = requestBody.get("dimensions");
            Map<String, Double> dimensions = null;

            if (dimensionsObj instanceof Map<?, ?>) {
                dimensions = ((Map<?, ?>) dimensionsObj).entrySet().stream()
                        .filter(e -> e.getKey() instanceof String && e.getValue() instanceof Number)
                        .collect(Collectors.toMap(
                                e -> (String) e.getKey(),
                                e -> ((Number) e.getValue()).doubleValue()));
            }

            if (dimensions == null) {
                return ResponseEntity.badRequest().body("Invalid dimensions format.");
            }

            Product newProduct = productService.create(storeId, productId, skuId, categoryId, name, dimensions);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // -------read the company in json format with pagination----------
    @GetMapping(value = "/products", produces = { "application/json" })
    ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        Page<Product> response = productService.findAll(pageable);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // --------------------update the product --------------------
    @PutMapping(value = "/product/{id}", consumes = { "application/json" }, produces = {
            "application/json" })
    public ResponseEntity<?> update(@PathVariable(value = "id") Long productId,
            @RequestBody Product productDetails) {
        Product response = productService.update(productId, productDetails);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // ---------------------delete the product ---------------------
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

    // ------------------------search product ------------------------
    @GetMapping("/product")
    public ResponseEntity<?> findAll(@RequestParam(value = "search") String search) {
        ProductSpecificationsBuilder builder = new ProductSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Product> spec = builder.build();
        List<Product> response = productService.findAll(spec);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendedProducts(@RequestBody RecommendationRequest request,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Double tolerance = request.getTolerance();
            Map<String, Double> bodyMeasurements = request.getMeasurements();
            List<String> categories = request.getCategory();
            Page<ProductDto> recommendedProducts = productService.getRecommendedProducts(bodyMeasurements, tolerance,
                    categories,
                    pageable);
            return ResponseEntity.ok(recommendedProducts);
        } catch (Exception e) {
            // e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
