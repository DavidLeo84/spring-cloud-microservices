package co.edu.uniquindio.client;

import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product detailsProduct(@PathVariable Long id);

    @PostMapping
    void createProduct(@RequestBody ProductDTO dto);

    @PutMapping
    ProductDTO updateProduct(@RequestBody ProductDTO dto);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Long id);

}
