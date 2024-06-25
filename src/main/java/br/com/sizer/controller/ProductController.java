package br.com.sizer.controller;

import br.com.sizer.utill.ProductSpecificationsBuilder;
import br.com.sizer.model.Product;
import br.com.sizer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    // -------------------create product -------------------
    @PostMapping("/product")
    ResponseEntity<?> createProduct(@RequestBody Product request) {
        Product response = productService.create(request);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // -------read the company in json format with pagination----------
    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = { "application/json" })
    ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        Page<Product> response = productService.findAll(pageable);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // --------------------update the product --------------------
    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
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
    @RequestMapping(method = RequestMethod.GET, value = "/product")
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
    public ResponseEntity<Page<Product>> recommendProducts(
            @RequestParam Map<String, Double> measurements,
            @RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> recommendedProducts = productService.recommendProducts(measurements, categoryId, pageable);
        return ResponseEntity.ok(recommendedProducts);
    }
}
