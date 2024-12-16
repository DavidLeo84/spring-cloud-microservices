package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dtos.MessageDTO;
import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProducts() throws Exception {

        try {
            productService.getProducts();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailsProduct(@PathVariable Long id) throws InterruptedException {
        /*if (id.equals(10L)) {
            throw new IllegalStateException("Product details not available");
        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }
        ProductDTO dto = productService.getProduct(id);

        if (dto != null) {
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));*/
        //Bloque comentado para la implementación de las pruebas con el circuit-breaker
        try {
            productService.getProduct(id);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        }
        return ResponseEntity.ok().body(this.productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO<String>> createProduct(@RequestBody ProductDTO productDTO) {

        productService.createProduct(productDTO);
        return ResponseEntity.ok().body(new MessageDTO<>(false, "Producto creado correctamente"));
    }

    @PutMapping
    public ResponseEntity<MessageDTO<ProductDTO>> updateProduct(@RequestBody ProductDTO dto) {

        ProductDTO productDTO;
        try {
            productDTO = productService.updateProduct(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageDTO<>(false, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
