package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        var products = repository.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Void> registerProduct(@RequestBody @Valid RequestProductDTO data) {
        Product product = new Product(data);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProductDTO data) {
        Optional<Product> product = repository.findById(data.id());
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produt is not found");
        }
        Product selectedProduct = product.get();

        selectedProduct.setName(data.name());
        selectedProduct.setPrice_in_cents(data.price_in_cents());

        repository.save(selectedProduct);

        return ResponseEntity.ok(selectedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        System.out.println("estou aqui: " + id);
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produt is not found");
        }

        Product selectedProduct = product.get();

        repository.delete(selectedProduct);

        return ResponseEntity.ok("Product deleted successfully.");
    }
}
