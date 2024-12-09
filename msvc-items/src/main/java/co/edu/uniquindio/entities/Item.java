package co.edu.uniquindio.entities;

import co.edu.uniquindio.libs.commons.msvc.libs.commons.entities.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    Product product;
    int quantity;

    public Double getTotal() {
        return product.getPrice() * quantity;
    }

}
