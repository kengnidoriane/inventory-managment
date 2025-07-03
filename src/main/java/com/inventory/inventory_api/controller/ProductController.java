package com.inventory.inventory_api.controller;


import com.inventory.inventory_api.model.Product;
import com.inventory.inventory_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "CRUD operations and stock alerts for products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Adds a new product with name, price and quantity")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.saveProduct(product);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products in the inventory")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a product given its unique ID")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates the price or quantity of an existing product")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.getProductById(id)
                .map(existing -> {
                    existing.setName(productDetails.getName());
                    existing.setPrice(productDetails.getPrice());
                    existing.setQuantityInStock(productDetails.getQuantityInStock());
                    return ResponseEntity.ok(productService.saveProduct(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID from the inventory")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get products with low stock", description = "Returns products with quantity less than 5")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }
}

