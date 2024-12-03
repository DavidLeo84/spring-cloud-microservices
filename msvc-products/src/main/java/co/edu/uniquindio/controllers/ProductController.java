package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dtos.MessageDTO;
import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Product;
import co.edu.uniquindio.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
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
        if (id.equals(10L)) {
            throw new IllegalStateException("Product details not available");
        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(1L);
        }
        ProductDTO dto = productService.getProduct(id);

        if (dto != null) {
            return ResponseEntity.ok().body(this.productService.getProduct(id));
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        /*try {
            ProductDTO dto =  productService.getProduct(id);
        }
        catch (Exception e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        }
        return ResponseEntity.ok().body(this.productService.getProduct(id));*/
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO<String>> createProduct(@RequestBody ProductDTO productDTO) throws Exception {

        productService.createProduct(productDTO);
        return ResponseEntity.ok().body(new MessageDTO<>(false, "Producto creado correctamente"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageDTO<String>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws Exception {

        try {
            productService.updateProduct(productDTO, id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(new MessageDTO<>(false, "Producto actualizado correctamente"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO<String>> deleteProduct(@PathVariable Long id) throws Exception {

        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new MessageDTO<>(false, "Producto eliminado correctamente"));
    }
}
