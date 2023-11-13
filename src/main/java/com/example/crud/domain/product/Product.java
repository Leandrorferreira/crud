package com.example.crud.domain.product;

import jakarta.persistence.*;
import lombok.*;

@Table(name="product")
@Entity(name="product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private Integer price_in_cents;

    public Product(RequestProductDTO dto) {
        this.name = dto.name();
        this.price_in_cents = dto.price_in_cents();
    }
}
