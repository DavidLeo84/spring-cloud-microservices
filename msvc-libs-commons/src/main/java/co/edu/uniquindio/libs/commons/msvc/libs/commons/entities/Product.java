package co.edu.uniquindio.libs.commons.msvc.libs.commons.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Double price;

    @Column(name = "create_at")
    LocalDate createAt;

    @Transient
    private int port;

    @Builder
    public Product(String name, Double price, LocalDate createAt) {
        this.name = name;
        this.price = price;
        this.createAt = createAt;
    }
}
