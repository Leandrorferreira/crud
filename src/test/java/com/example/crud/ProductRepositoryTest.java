package com.example.crud;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProductDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Product successfully from database")
    void findByIdSuccess() {
        RequestProductDTO dto = new RequestProductDTO("123456", "Product", 4500);
        this.createProduct(dto);

        List<Product> products = this.repository.findAll();

        var result = products.get(0);

        assertThat(Objects.equals(result.getName(), dto.name())).isTrue();
        assertThat(Objects.equals(result.getPrice_in_cents(), dto.price_in_cents())).isTrue();
    }

    @Test
    @DisplayName("Should not get Product from database when product not exists")
    void findByIdError() {
        RequestProductDTO dto = new RequestProductDTO("123456", "Product", 4500);

        List<Product> products = this.repository.findAll();

        assertThat(products.isEmpty()).isTrue();
    }

    private void createProduct(RequestProductDTO dto) {
        Product product = new Product(dto);
        this.entityManager.persist(product);
    }
}
